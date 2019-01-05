package yao.muyuer.dailyrecord.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.adapter.GridViewAdapter;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.library.activity.base.BaseActivity;
import yao.muyuer.library.datepicker.CustomDatePicker;

public class AddBillActivity extends BaseActivity implements OnClickListener {

	/**
	 * adapter相关变量
	 */
	private GridViewEntity ent;
	private GridViewAdapter adapter;
	private GridView gridview;
	//private Integer[] img;
	//private String[] st;
	private List<GridViewEntity> list;

	/**
	 * addBill相关变量
	 */

	private Button btn_in, btn_out, btn_date,btn_content;
	private TextView changedshow_text;
	private ImageView changedshow_jpg;
	private Button cash_btn;
	
	private ImageView imgCut;
	private File mCurrentPhotoFile;
	private Bitmap cameraBitmap = null;
	
	/**
	 * 添加数据到库相关变量
	 */
	private Intent intent;
	private static final int REQ_IMAGE_CODE = 101;
	private static final int REQ_IMAGE_CUT_CODE = 102;
	private static final int REQ_IMAGE_BOOK_CODE = 103;
	private static final int REQ_CASH_CODE = 104;
	private static final int REQ_CONTENT_CODE = 105;
	private String accountname ;
	private double money;
	private int indeximg;
	private int amountType=0;
	private String amountTile;
	private GridViewEntity inent;
	private UserService service;
	private GridViewEntity dbent;
	private Long e;// 判断数据是否插入成功
	private CustomDatePicker customDatePicker1;

	private Integer billId = -1;
	private int bookId = 1;
	private int accountId = 1;
	private int amountId ;
	private String content = "";
	private String addtime = "";

	/**
	 * 自定义监听器
	 */
	private MyGridLister grlister;

	/**
	 * 计算器相关变量
	 */
	private TextView one, four, seven, ac, two, five, eight, zero, three, six, nine, point, del, add, dec,ok;
	private TextView changedshow_number;
	private boolean isChoicked = false;
	private String intext1 = null;
	private String intext2 = null;
	private String intext3 = null;
	private String intext4 = null;
	private String intext5 = null;
	private String intext6 = null;
	private String intext7 = null;
	private String intext8 = null;
	private String intext9 = null;
	private String intext0 = null;
	private String intextpoint = null;

	private double result = 0;
	private double in1 = 0;
	private double in2 = 0;
	private int opcount = 0;
	private Integer[] icons1, icons2;
	private String[] titles1, titles2;


	@Override
	protected void setUpView() {
		gridview = $(R.id.gridview);
		btn_out = $(R.id.btn_out);
		btn_in = $(R.id.btn_in);
		btn_out.setOnClickListener(this);
		btn_in.setOnClickListener(this);
		btn_date = $(R.id.btn_date);
		btn_date.setOnClickListener(this);
		btn_content= $(R.id.btn_content);
		btn_content.setOnClickListener(this);
		cash_btn = $(R.id.btn_cash);
		cash_btn.setOnClickListener(this);
		changedshow_text = $(R.id.changedshow_text);
		changedshow_jpg = $(R.id.changedshow_jpg);
		grlister = new MyGridLister();
		gridview.setOnItemClickListener(grlister);
		imgCut = $(R.id.imgCut);
		imgCut.setOnClickListener(this);

		initAmountType();
		initView();
		initSelectColor();
		initCalculator();
		initDatePicker();

		Intent intent = getIntent();
		billId = intent.getIntExtra("billid", -1);
		if (billId != null && billId != -1)
			try {
				initData(billId);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	}
	@Override
	protected int setLayoutResourceID() {
		return R.layout.add_bill_layout;
	}

	@Override
	protected void setUpData() {

	}

	private void initDatePicker() {
		SimpleDateFormat sdf = new SimpleDateFormat(Config.DATA_FORMAT, Locale.CHINA);
		String now = sdf.format(new Date());
		btn_date.setText(now.split(" ")[0]);
		addtime = now;

		customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
			@Override
			public void handle(String time) { // 回调接口，获得选中的时间
				addtime = time;
				SimpleDateFormat sdf2 = new SimpleDateFormat(Config.DATA_FORMAT2);
				btn_date.setText(sdf2.format(addtime));
			}
		}, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
		customDatePicker1.showSpecificTime(true); // 不显示时和分
		customDatePicker1.setIsLoop(false); // 不允许循环滚动
	}
	private void initAmountType(){
		service = new UserService(AddBillActivity.this);
		icons1 = service.GetAmountTypeIcon(1);
		titles1 = service.GetAmountTypeTitle(1);
		icons2 = service.GetAmountTypeIcon(2);
		titles2 = service.GetAmountTypeTitle(2);
	}
	private void initView() {
		ent = new GridViewEntity(icons1, titles1);
		list = new ArrayList<GridViewEntity>();
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		adapter = new GridViewAdapter(list, this);
		gridview.setAdapter(adapter);
	}

	private void incomeIsClick() {
		ent = new GridViewEntity(icons2, titles2);
		list = new ArrayList<GridViewEntity>();
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);
		list.add(ent);

		adapter = new GridViewAdapter(list, this);
		adapter.notifyDataSetChanged();
		gridview.setAdapter(adapter);
	}

	/**
	 * 实例化计算器相关
	 */
	private void initCalculator() {
		one = (TextView) findViewById(R.id.one);
		one.setOnClickListener(this);
		two = (TextView) findViewById(R.id.two);
		two.setOnClickListener(this);
		three = (TextView) findViewById(R.id.three);
		three.setOnClickListener(this);
		four = (TextView) findViewById(R.id.four);
		four.setOnClickListener(this);
		five = (TextView) findViewById(R.id.five);
		five.setOnClickListener(this);
		six = (TextView) findViewById(R.id.six);
		six.setOnClickListener(this);
		seven = (TextView) findViewById(R.id.seven);
		seven.setOnClickListener(this);
		eight = (TextView) findViewById(R.id.eight);
		eight.setOnClickListener(this);
		nine = (TextView) findViewById(R.id.nine);
		nine.setOnClickListener(this);
		zero = (TextView) findViewById(R.id.zero);
		zero.setOnClickListener(this);
		ac = (TextView) findViewById(R.id.ac);
		ac.setOnClickListener(this);
		add = (TextView) findViewById(R.id.add);
		add.setOnClickListener(this);
		del = (TextView) findViewById(R.id.del);
		del.setOnClickListener(this);
		dec = (TextView) findViewById(R.id.dec);
		dec.setOnClickListener(this);
		point = (TextView) findViewById(R.id.point);
		point.setOnClickListener(this);
		ok = (TextView) findViewById(R.id.ok);
		ok.setOnClickListener(this);		
		changedshow_number = (TextView) findViewById(R.id.changedshow_number);

	}

	private void initData(Integer billId) throws ParseException {
		GridViewEntity ent = service.findBill(billId);
		bookId = ent.getBookId();
		accountId = ent.getAccountId();
		content = ent.getContent();
		amountId = ent.getAmountId();
		indeximg = amountId-1;
		amountTile = ent.getAmountTile();
		byte[] img = ent.getImage();
		if (img!= null && img.length > 0) {
			cameraBitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
			imgCut.setImageBitmap(cameraBitmap);
		}
		changedshow_number.setText(Double.toString(ent.getMoney()));
		SimpleDateFormat sdf = new SimpleDateFormat(Config.DATA_FORMAT);
		SimpleDateFormat sdf2 = new SimpleDateFormat(Config.DATA_FORMAT2);
		btn_date.setText(sdf2.format(ent.getAddTime()));
		addtime = sdf.format(ent.getAddTime());

		int state=ent.getAmountType();
		if(state==0){
			int path = icons1[ent.getIcon()];
			changedshow_jpg.setImageResource(path);
			String text = titles1[ent.getIcon()];
			changedshow_text.setText(text);
			btn_date.setText(sdf.format(ent.getAddTime()));
			//btn_date.setText(ent.getIcon());//.split(" ")[0]
			btn_out.setBackground(btn_out.getResources().getDrawable(R.drawable.shape_buttom_active));
			btn_in.setBackground(btn_in.getResources().getDrawable(R.drawable.shape_buttom));
		}else if(state==1){
			int path = icons2[ent.getIcon()];
			changedshow_jpg.setImageResource(path);
			String text = titles2[ent.getIcon()];
			changedshow_text.setText(text);
			btn_date.setText(sdf.format(ent.getAddTime()));//.split(" ")[0]
			btn_out.setBackground(btn_out.getResources().getDrawable(R.drawable.shape_buttom));
			btn_in.setBackground(btn_in.getResources().getDrawable(R.drawable.shape_buttom_active));
		}

	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";

	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_cash:
				intent = new Intent(AddBillActivity.this, DialogActivity.class);
				this.startActivityForResult(intent, REQ_CASH_CODE);
				break;
			case R.id.btn_date:
				// 日期格式为yyyy-MM-dd
				customDatePicker1.show(addtime);
				break;
			case R.id.btn_content:
				intent = new Intent(AddBillActivity.this, AddContentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("content", content);
				intent.putExtras(bundle);
				AddBillActivity.this.startActivityForResult(intent, REQ_CONTENT_CODE);
				break;
			case R.id.imgCut:
				AlertDialog.Builder normalDialog =
						new AlertDialog.Builder(AddBillActivity.this);
				normalDialog.setIcon(R.drawable.icon);
				normalDialog.setTitle("设置头像").setMessage("选择一张图片来设置您的头像");
				normalDialog.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// ...To-do
							}
						});
				normalDialog.setNeutralButton("从相机",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mCurrentPhotoFile = new File("mnt/sdcard/DCIM/Camera/", getPhotoFileName());
								Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								intent2.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(mCurrentPhotoFile));
								startActivityForResult(intent2, REQ_IMAGE_CODE);
							}
						});
				normalDialog.setNegativeButton("从相册", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1 = new Intent(Intent.ACTION_PICK, null);
						intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent1, REQ_IMAGE_BOOK_CODE);
					}
				});
				// 创建实例并显示
				normalDialog.show();
			case R.id.btn_out:
				initView();
				btn_out.setBackground(btn_out.getResources().getDrawable(R.drawable.shape_buttom_active));
				btn_in.setBackground(btn_in.getResources().getDrawable(R.drawable.shape_buttom));
				amountType = 0;
				break;
			case R.id.btn_in:
				incomeIsClick();
				btn_out.setBackground(btn_out.getResources().getDrawable(R.drawable.shape_buttom));
				btn_in.setBackground(btn_in.getResources().getDrawable(R.drawable.shape_buttom_active));
				amountType = 1;
				break;
			case R.id.one:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext1 = changedshow_number.getText().toString();
				intext1 += "1";
				changedshow_number.setText(intext1);
				break;
			case R.id.two:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext2 = changedshow_number.getText().toString();
				intext2 += "2";
				changedshow_number.setText(intext2);
				break;
			case R.id.three:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext3 = changedshow_number.getText().toString();
				intext3 += "3";
				changedshow_number.setText(intext3);
				break;
			case R.id.four:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext4 = changedshow_number.getText().toString();
				intext4 += "4";
				changedshow_number.setText(intext4);
				break;
			case R.id.five:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext5 = changedshow_number.getText().toString();
				intext5 += "5";
				changedshow_number.setText(intext5);
				break;
			case R.id.six:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext6 = changedshow_number.getText().toString();
				intext6 += "6";
				changedshow_number.setText(intext6);
				break;
			case R.id.seven:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext7 = changedshow_number.getText().toString();
				intext7 += "7";
				changedshow_number.setText(intext7);
				break;
			case R.id.eight:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext8 = changedshow_number.getText().toString();
				intext8 += "8";
				changedshow_number.setText(intext8);
				break;
			case R.id.nine:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext9 = changedshow_number.getText().toString();
				intext9 += "9";
				changedshow_number.setText(intext9);
				break;
			case R.id.zero:
				if (isChoicked) {
					changedshow_number.setText(null);
					isChoicked = false;
				}
				intext0 = changedshow_number.getText().toString();
				intext0 += "0";
				changedshow_number.setText(intext0);
				break;

			case R.id.point:
				intextpoint = changedshow_number.getText() + ".";
				changedshow_number.setText(intextpoint);
				break;
			case R.id.add:
				if (changedshow_number.getText().toString().equals(null)
						|| changedshow_number.getText().toString().equals("")) {
					return;
				}
				in1 = Double.parseDouble(changedshow_number.getText().toString());
				changedshow_number.setText(null);
				opcount = 1;
				isChoicked = false;
				break;
			case R.id.ac:
				if (changedshow_number.getText().toString().equals(null)
						|| changedshow_number.getText().toString().equals("")) {
					return;
				}

				changedshow_number.setText(null);
				opcount = 2;
				isChoicked = false;
				break;
			case R.id.del:
				if (changedshow_number.getText().toString().equals(null)
						|| changedshow_number.getText().toString().equals("")) {
					return;
				}
				String st = changedshow_number.getText().toString();
				changedshow_number.setText(st.subSequence(0, st.length() - 1));
				opcount = 3;
				isChoicked = false;
				break;

			case R.id.ok:
				if (changedshow_number.getText().toString().equals(null)
						|| changedshow_number.getText().toString().equals("")) {
					return;
				}
				in2 = Double.parseDouble(changedshow_number.getText().toString());
				changedshow_number.setText(null);
				result = in1 + in2;
				changedshow_number.setText(Double.toString(result));
				isChoicked = true;
				money = Double.valueOf(changedshow_number.getText().toString().trim());
				in1 = 0;
				in2 = 0;
				amountTile = changedshow_text.getText().toString().trim();
				//addtime = btn_date.getText().toString();
				SimpleDateFormat sdf = new SimpleDateFormat(Config.DATA_FORMAT);//小写的mm表示的是分钟
				Date date = null;
				try {
					date = sdf.parse(addtime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				//intime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
				inent = new GridViewEntity();
				inent.setBillId(billId);
				inent.setBookId(bookId);
				inent.setAmountType(amountType);
				inent.setAmountId(amountId);
				inent.setAmountTile(amountTile);
				inent.setAccountId(accountId);
				inent.setMoney(money);
				inent.setIcon(indeximg);
				inent.setContent(content);
				byte[] imgBytes = null;
				if(cameraBitmap!= null)
					imgBytes = BitmapToBytes(cameraBitmap);
				inent.setImage(imgBytes);
				inent.setAddTime(date);
				try {
					insertBill(inent);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				UpdataAccount(inent);
				AddBillActivity.this.finish();
				break;
			default:
				break;
		}
	}

	public byte[] BitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	private void initSelectColor() {
		btn_out.setBackground(btn_out.getResources().getDrawable(R.drawable.shape_buttom_active));
		btn_in.setBackground(btn_in.getResources().getDrawable(R.drawable.shape_buttom));
		
		//pay_btn.setBackgroundColor(pay_btn.getResources().getColor(R.color.blue));
		//income_btn.setBackgroundColor(pay_btn.getResources().getColor(R.color.white));
		//pay_btn.setTextColor(pay_btn.getResources().getColor(R.color.white));
		//income_btn.setTextColor(income_btn.getResources().getColor(R.color.black));

	}

	/**
	 * gridview监听
	 * 
	 * @author Administrator
	 *
	 */

	private class MyGridLister implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			ent = list.get(position);
			changedShow(ent, position);

			indeximg = position;
		}

	}

	/**
	 * 此方法可展示的gridview单击结果
	 */
	private void changedShow(GridViewEntity ent, int position) {

		Integer[] img = ent.getImgs();
		int path = img[position];
		changedshow_jpg.setImageResource(path);
		String[] st = ent.getTypes();
		String text = st[position];
		changedshow_text.setText(text);
		amountId = position + 1;
	}
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX : aspectY
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY the height and width
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQ_IMAGE_CUT_CODE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
			case REQ_IMAGE_CODE:
				// after select from gallery
				if (resultCode == RESULT_OK) {
					cropPhoto(Uri.fromFile(mCurrentPhotoFile));
					//cropPhoto(Uri.fromFile(mCurrentPhotoFile));
				}
				break;
			case REQ_IMAGE_BOOK_CODE:
				// after taking a photo
				if (resultCode == RESULT_OK) {
					cropPhoto(data.getData());
				}
				break;
			case REQ_IMAGE_CUT_CODE:
					if (data != null) {
						Bundle extras = data.getExtras();
						cameraBitmap = extras.getParcelable("data");
						if(cameraBitmap != null) {
							//setPicToView(logoBitmap);
							imgCut.setImageBitmap(cameraBitmap);
						}
					}
					//cameraBitmap = (Bitmap) data.getExtras().get("data");
					//imgCut.setImageBitmap(cameraBitmap);
				break;
			case REQ_CASH_CODE:
				if (data != null) {
					accountname = data.getStringExtra("AccountName");
					accountId = data.getIntExtra("AccountId", -1);
					cash_btn.setText(accountname);
				}
				break;
			case REQ_CONTENT_CODE:
				if (data != null) {
					content = data.getStringExtra("content");
				}
				break;
			default:
				break;
		}
	}

	private long insertBill(GridViewEntity ent) throws ParseException {
		service = new UserService(AddBillActivity.this);
		SimpleDateFormat sdf = new SimpleDateFormat(Config.DATA_FORMAT2);
		String addtime = sdf.format(ent.getAddTime());
		dbent = service.findBill(Integer.toString(ent.getBillId()));
		if (dbent != null) {
			e = service.updateBill(ent);
		} else {
			dbent = service.findBillByType(Integer.toString(inent.getAmountId()), addtime);
			if (dbent != null) {
				/**
				 * 当要插入账单记录时间和账单类型相同时
				 */
				Double dbbill = dbent.getMoney();
				Double newbill = inent.getMoney();
				String bill = Double.toString(dbbill + newbill);
				String id = Integer.toString(dbent.getBillId());
				ContentValues values = new ContentValues();
				values.put("Money", bill);
				e = service.updateBillMoney(id, values);

			} else {
				e = service.insertBill(ent);
			}
		}
		return e;
	}

	private void UpdataAccount(GridViewEntity gve) {
		service = new UserService(AddBillActivity.this);
		service.updateAccount(gve);

	}
}
