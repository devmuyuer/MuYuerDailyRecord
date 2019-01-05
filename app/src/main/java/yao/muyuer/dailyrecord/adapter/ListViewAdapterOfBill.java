package yao.muyuer.dailyrecord.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.activity.AddBillActivity;
import yao.muyuer.dailyrecord.activity.BillDataActivity;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.skinlibrary.base.SkinBaseActivity;

public class ListViewAdapterOfBill extends BaseAdapter {

	private Context context;
	private List<GridViewEntity> list;
	private HodView hod;
	private View currView;
	private Bitmap bitmap = null;

	public ListViewAdapterOfBill(Context context,
			List<GridViewEntity> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list!=null){
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			hod=new HodView();
			convertView= LayoutInflater.from(context).inflate(R.layout.bill_item, null);
		    hod.bill_right_show_text=(TextView) convertView.findViewById(R.id.bill_right_show_text);
			hod.bill_left_show_text=(TextView) convertView.findViewById(R.id.bill_left_show_text);
			hod.bill_left_show_text_desc=(TextView) convertView.findViewById(R.id.bill_left_show_text_desc);
			hod.bill_right_show_text_desc=(TextView) convertView.findViewById(R.id.bill_right_show_text_desc);

		    hod.imgbill=(ImageView) convertView.findViewById(R.id.imgbill);
			hod.imgmodify=(ImageView) convertView.findViewById(R.id.imgmodify);
			hod.imgdelete=(ImageView) convertView.findViewById(R.id.imgdelete);

			hod.imgbill_left_img=(ImageView) convertView.findViewById(R.id.imgbill_left_img);
			hod.imgbill_right_img=(ImageView) convertView.findViewById(R.id.imgbill_right_img);
		    
		    hod.time_text=(TextView) convertView.findViewById(R.id.txtTime);
		    hod.hint_text=(TextView) convertView.findViewById(R.id.txtHint);
		    
		    hod.xianup=(View) convertView.findViewById(R.id.xianup);
			hod.xiandown=(View) convertView.findViewById(R.id.xiandown);

		    hod.rlmain=(RelativeLayout) convertView.findViewById(R.id.rlmain);
		    hod.llhint=(LinearLayout) convertView.findViewById(R.id.llhint);
			hod.postion = position;
		    convertView.setTag(hod);

			currView = convertView;
		}else{
			hod=(HodView)convertView.getTag();
		}
	   
		hod.imgbill.setImageResource(list.get(position).getIcon());
		int state=list.get(position).getAmountType();
		if(state==0){
			hod.bill_right_show_text.setText(list.get(position).getAmountTile()+"  "+list.get(position).getMoney());
			hod.bill_right_show_text.setVisibility(View.VISIBLE);
			hod.bill_left_show_text.setVisibility(View.GONE);

			if (list.get(position).getContent().isEmpty())
				hod.bill_right_show_text_desc.setVisibility(View.GONE);
			else {
				String content = list.get(position).getContent();
				if (content.length() >= 10)
					content = content.substring(0,9) + "...";
				hod.bill_right_show_text_desc.setText(content);
				hod.bill_right_show_text_desc.setVisibility(View.VISIBLE);
			}
			hod.bill_left_show_text_desc.setVisibility(View.GONE);

			byte[] img = list.get(position).getImage();
			if (img!= null && img.length > 0) {
				bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
				hod.imgbill_left_img.setImageBitmap(bitmap);
				hod.imgbill_left_img.setVisibility(View.VISIBLE);
			}
			else
				hod.imgbill_left_img.setVisibility(View.GONE);
			hod.imgbill_right_img.setVisibility(View.GONE);
		}
		if(state==1){
			hod.bill_left_show_text.setText(list.get(position).getAmountTile()+"  "+list.get(position).getMoney());
		    hod.bill_left_show_text.setVisibility(View.VISIBLE);
		    hod.bill_right_show_text.setVisibility(View.GONE);

			if (list.get(position).getContent().isEmpty())
				hod.bill_left_show_text_desc.setVisibility(View.GONE);
			else {
				String content = list.get(position).getContent();
				if (content.length() >= 10)
					content = content.substring(0,9) + "...";
				hod.bill_left_show_text_desc.setText(content);
				hod.bill_left_show_text_desc.setVisibility(View.VISIBLE);
			}
			hod.bill_right_show_text_desc.setVisibility(View.GONE);

			byte[] img = list.get(position).getImage();
			if (img!= null && img.length > 0) {
				bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
				hod.imgbill_right_img.setImageBitmap(bitmap);
				hod.imgbill_right_img.setVisibility(View.VISIBLE);
			}
			else
				hod.imgbill_right_img.setVisibility(View.GONE);
			hod.imgbill_left_img.setVisibility(View.GONE);
		}
		hod.llhint.setVisibility(View.GONE);
		//添加开始信息
		if (position == list.size()-1){			
			hod.bill_left_show_text.setVisibility(View.GONE);
			hod.bill_right_show_text.setVisibility(View.GONE);
			hod.xiandown.setVisibility(View.GONE);
			hod.bill_right_show_text_desc.setVisibility(View.GONE);
			hod.bill_left_show_text_desc.setVisibility(View.GONE);
			hod.imgbill_right_img.setVisibility(View.GONE);
			hod.imgbill_left_img.setVisibility(View.GONE);

			hod.time_text.setText(Config.FirstInstallTime);
			hod.hint_text.setText("您开启了天天记记账旅程");
		    
		    hod.llhint.setVisibility(View.VISIBLE);
			hod.imgbill.setImageResource(R.drawable.type_big_0);
		}
		else
			hod.xiandown.setVisibility(View.VISIBLE);

		if (list.get(position).getIsSel()) {
			hod.imgmodify.setVisibility(View.VISIBLE);
			hod.imgdelete.setVisibility(View.VISIBLE);
		}
		else
		{
			hod.imgmodify.setVisibility(View.GONE);
			hod.imgdelete.setVisibility(View.GONE);
		}

		//设置监听事件
		final View finalConvertView = convertView;
		final ImageView imgmodify = hod.imgmodify;
		final ImageView imgdelete = hod.imgdelete;
		final ImageView imgbill = hod.imgbill;
		final int finalPostion = position;//hod.postion;

		hod.imgbill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "点击的是ImageButton"+v.getTag(), 1).show();
				for (int i=0;i<list.size();i++)
					list.get(i).setIsSel(false);
				if (finalPostion < list.size() - 1)
					list.get(finalPostion).setIsSel(!list.get(finalPostion).getIsSel());
				notifyDataSetChanged();
			}
		});

		hod.imgmodify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, AddBillActivity.class);
				intent.putExtra("billid", list.get(finalPostion).getBillId());
				context.startActivity(intent);
				notifyDataSetChanged();
			}
		});
		hod.imgdelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder normalDialog =
						new AlertDialog.Builder(context);
				normalDialog.setIcon(R.drawable.icon);
				normalDialog.setTitle("提示").setMessage("确定要删除这条记账记录吗？");
				normalDialog.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// ...To-do
							}
						});
				normalDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						UserService service = new UserService(context);
						service.delBill(list.get(finalPostion).getBillId());
						list.remove(finalPostion);
						notifyDataSetChanged();
					}
				});
				// 创建实例并显示
				normalDialog.show();
			}
		});
		hod.imgmodify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, AddBillActivity.class);
				intent.putExtra("billid", list.get(finalPostion).getBillId());
				context.startActivity(intent);
				notifyDataSetChanged();
			}
		});
		hod.imgbill_left_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, BillDataActivity.class);
				intent.putExtra("billid", list.get(finalPostion).getBillId());
				context.startActivity(intent);
			}
		});
		hod.imgbill_right_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, BillDataActivity.class);
				intent.putExtra("billid", list.get(finalPostion).getBillId());
				context.startActivity(intent);
			}
		});
	    return convertView;
	}

	private  void setImgAnimation(int height, int width){
		/*hod.imgmodify.setTop(height/2);
		hod.imgmodify.setLeft(width/2 - hod.imgmodify.getWidth());
		hod.imgmodify.setVisibility(View.VISIBLE);*/

		hod.imgmodify.setTop(100);
		hod.imgmodify.setLeft(10);
		hod.imgmodify.setVisibility(View.VISIBLE);
		hod.imgdelete.setTop(200);
		hod.imgdelete.setLeft(800);
		hod.imgdelete.setVisibility(View.VISIBLE);


		/*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imgmodify.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.LEFT_OF, 0);
		imgmodify.setLayoutParams(params);*/

		/*hod.imgdelete.setTop(height/2);
		hod.imgdelete.setLeft(width/2 + hod.imgmodify.getWidth());
		hod.imgdelete.setVisibility(View.VISIBLE);*/

		AnimationSet setMod =new AnimationSet(true);
		LinearInterpolator selectInterpolatorMod = new LinearInterpolator();
		AlphaAnimation alphaAninMod = new AlphaAnimation(0.5f,1.0f);
		setMod.addAnimation(alphaAninMod);
		TranslateAnimation tranTranslateMod=new TranslateAnimation(
				hod.imgmodify.getLeft(),
				10,
				hod.imgmodify.getTop(),
				hod.imgmodify.getTop());
		setMod.addAnimation(tranTranslateMod);
		setMod.setInterpolator(selectInterpolatorMod);
		setMod.setDuration(800);

		AnimationSet setDel =new AnimationSet(true);
		LinearInterpolator selectInterpolatorDel = new LinearInterpolator();
		AlphaAnimation alphaAninDel = new AlphaAnimation(0.5f,1.0f);
		setDel.addAnimation(alphaAninDel);
		TranslateAnimation tranTranslateDel=new TranslateAnimation(
				hod.imgmodify.getLeft(),
				width-10,
				hod.imgmodify.getTop(),
				hod.imgmodify.getTop());
		setDel.addAnimation(tranTranslateDel);
		setDel.setInterpolator(selectInterpolatorDel);
		setDel.setDuration(800);

		/*hod.imgmodify.startAnimation(setMod);
		hod.imgdelete.startAnimation(setDel);*/
	}

	private class HodView{
		TextView bill_right_show_text,bill_left_show_text, time_text, hint_text, bill_left_show_text_desc, bill_right_show_text_desc;
		ImageView imgbill,imgmodify,imgdelete, imgbill_left_img,imgbill_right_img;
		View xianup,xiandown;
		RelativeLayout rlmain;
		LinearLayout llhint;
		int postion;
	}
}