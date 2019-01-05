package yao.muyuer.dailyrecord.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.dailyrecord.entity.SystemEntity;
import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.library.other.CircleImageView;
import yao.muyuer.library.other.WaveProgressView;
import yao.muyuer.skinlibrary.loader.SkinManager;

import static android.app.Activity.RESULT_OK;

public class SettingActivity extends BaseFragment implements OnClickListener {

	private File mCurrentPhotoFile;
	private Bitmap cameraBitmap = null;

	private static final int REQ_IMAGE_CODE = 101;
	private static final int REQ_IMAGE_CUT_CODE = 102;
	private static final int REQ_IMAGE_BOOK_CODE = 103;

	private RelativeLayout rl_about, rl_account, rl_setting, rl_font, rl_email,rl_skin;
	private Intent intent;
	private UserService service;
	private List<GridViewEntity> dblist;
	TextView tv_name, tvHint, txtHint1, txtHint2;
	CircleImageView iv_avatar;
	EditText et;
	private WaveProgressView bnp;

	@Override
	protected int setLayoutResourceID() {
		return R.layout.setting_layout;
	}

	@Override
	protected void setUpView() {
		rl_about = $(R.id.rl_about);
		rl_account = $(R.id.rl_account);
		rl_setting = $(R.id.rl_setting);
		rl_email = $(R.id.rl_email);
		rl_skin = $(R.id.rl_skin);
		rl_font = $(R.id.rl_font);

		iv_avatar = $(R.id.iv_Img);
		tv_name = $(R.id.tv_name);
		bnp = $(R.id.ns_bar);

		iv_avatar.setOnClickListener(this);
		tv_name.setOnClickListener(this);

		rl_about.setOnClickListener(this);
		rl_account.setOnClickListener(this);
		rl_setting.setOnClickListener(this);
		rl_font.setOnClickListener(this);
		rl_email.setOnClickListener(this);
		rl_skin.setOnClickListener(this);

		tvHint = $(R.id.tvHint);
		txtHint1 = $(R.id.txtHint1);
		txtHint2 = $(R.id.txtHint2);
		iniData();
	}
	private void iniData() {
		et = new EditText(getActivity());
		tvHint.setText("记账第" + Config.FoundingTime + "，原你经济独立，有事做有人爱");
		service = new UserService(this.getActivity());
		double zc=service.findBillMoney(0);
		txtHint1.setText("本月从1日开始已支出" + zc + "元");
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		txtHint2.setText("日均" + String.format("%.2f",zc/day) + "元");

		bnp.setCurrent(20, 20 + "%");

		SystemEntity ent = new SystemEntity();
		try {
			ent = service.findSystem();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		byte[] img = ent.GetUserLogo();
		if (img!= null && img.length > 0) {
			Bitmap logoBitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
			iv_avatar.setImageBitmap(logoBitmap);
		}
		tv_name.setText(ent.GetUserName());
	}
	public byte[] BitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	private void saveLogo(Bitmap logoBitmap)
	{
		ContentValues values = new ContentValues();
		byte[] imgBytes = null;
		if(logoBitmap!= null) {
			imgBytes = BitmapToBytes(logoBitmap);
			values.put("UserLogo", imgBytes);
			service.updateSystem(values);
		}
	}
	private void saveName(String name) {
		ContentValues values = new ContentValues();
		values.put("UserName", name);
		service.updateSystem(values);
	}
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";

	}

	HashMap<String, String> map = new HashMap<>();

	private void switchFont() {
		map.put("默认", null);
		map.put("时尚细黑", "SSXHZT.ttf");
		map.put("大梁体", "DLTZT.ttf");
		map.put("微软雅黑", "WRYHZT.ttf");
		new MaterialDialog.Builder(getActivity())
				.title("选择字体")
				.items(map.keySet())
				.itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
					@Override
					public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
						SkinManager.getInstance().loadFont(map.get(text));
						return true;
					}
				})
				.positiveText("确定")
				.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.rl_account:
				/*intent = new Intent(getActivity(), AccountActivity.class);
				startActivity(intent);*/
				break;
			case R.id.rl_email:
				break;
			case R.id.rl_skin:
				intent = new Intent(getActivity(), ChangeSkinActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_font:
				switchFont();
				break;
			case R.id.rl_setting:
				//intent = new Intent(getActivity(), RollOutOrIn.class);
				//startActivity(intent);
				break;
			case R.id.rl_about:
				intent = new Intent(getActivity(), AboutActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.iv_Img:
				AlertDialog.Builder normalDialog =
						new AlertDialog.Builder(getActivity());
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
				break;
			case R.id.tv_name:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("设置昵称").setIcon(android.R.drawable.ic_dialog_info).setView(et)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        tv_name.setText(input);
                        saveName(input);
                    }
                });
                builder.show();
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
			case REQ_IMAGE_CODE:
				if (resultCode == RESULT_OK) {
					cropPhoto(Uri.fromFile(mCurrentPhotoFile));
				}
				break;
			case REQ_IMAGE_BOOK_CODE:
				if (resultCode == RESULT_OK) {
					cropPhoto(data.getData());
				}
				break;
			case REQ_IMAGE_CUT_CODE:
				if (data != null) {
					Bundle extras = data.getExtras();
					cameraBitmap = extras.getParcelable("data");
					if(cameraBitmap != null) {
						iv_avatar.setImageBitmap(cameraBitmap);
						saveLogo(cameraBitmap);
					}
				}
				break;
			default:
				break;
		}
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
}
