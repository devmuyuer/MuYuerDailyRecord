package yao.muyuer.dailyrecord.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.library.activity.base.BaseActivity;

public class BillDataActivity extends BaseActivity implements OnClickListener {

	private Button btn_back;
	private Integer billId = -1;
	private UserService service;
	TextView txtAmountTile, tvAddTime,tvBill,tvContent;
	ImageView iv_Img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void setUpView() {
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);

		tvContent = (TextView) findViewById(R.id.tvContent);
		txtAmountTile = (TextView) findViewById(R.id.txtAmountTile);
		tvAddTime = (TextView) findViewById(R.id.tvAddTime);
		tvBill = (TextView) findViewById(R.id.tvBill);
		iv_Img= (ImageView) findViewById(R.id.iv_Img);

		service = new UserService(BillDataActivity.this);

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
		return R.layout.bill_data_layout;
	}

	private void initData(Integer billId) throws ParseException {
		GridViewEntity ent = service.findBill(billId);
		tvBill.setText(Double.toString(ent.getMoney()));
		txtAmountTile.setText(ent.getAmountTile());
		tvContent.setText(ent.getContent());
		byte[] img = ent.getImage();
		if (img!= null && img.length > 0) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
			iv_Img.setImageBitmap(bitmap);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat(Config.DATA_FORMAT2);
		tvAddTime.setText(sdf2.format(ent.getAddTime()));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_back:
				BillDataActivity.this.finish();
			default:
				break;
		}
	}
}
