package yao.muyuer.dailyrecord.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.adapter.ViewPagerAdapter;
import yao.muyuer.library.activity.base.BaseActivity;

public class IndexActivity extends BaseActivity {


	/**
	 * viewpager相关成员变量
	 */
	private ViewPager viewpager;
	private ViewPagerAdapter viewadapter;
	private List<Fragment> list;
	private FragmentManager fm;
	private ReportActivity freport;
	private BillActivity fbill;
	private SettingActivity fsetting;
	//private FgNoteBook fnotebook;

	private static String TAG = "IndexActivity";

	/**
	 * userinfo-bottom相关成员变量
	 */
	private View xian_setting,xian_bill, xian_rep; //xian_note,
	private RelativeLayout bill_layout,setting_layout, rep_layout;//notebook_layout,
	private TextView text_setting,text_bill, text_rep; //text_notebook,

	private MypagerLister pagerlister;
	private MyReLayoutLister relayoutlister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
			Log.i(TAG, "NULL");
		else {
			Log.i(TAG, "NOT NULL");
		}
		IniData();
	}
	@Override
	protected int setLayoutResourceID() {
		return R.layout.index_layout;
	}
	/**
	 * 实例化userinfo
	 */
	@Override
	protected void setUpView() {
		viewpager=$(R.id.viewpager);
		//notebook_layout=(RelativeLayout)findViewById(R.id.notebook_layout);
		rep_layout=$(R.id.rep_layout);
		bill_layout=$(R.id.bill_layout);
		setting_layout=$(R.id.setting_layout);
		//text_notebook=(TextView)findViewById(R.id.text_notebook);
		text_rep=$(R.id.text_rep);
		text_bill=$(R.id.text_bill);
		text_setting=$(R.id.text_setting);

		xian_rep=$(R.id.xian_rep);
		xian_bill=$(R.id.xian_bill);
		xian_setting=$(R.id.xian_setting);
		//xian_note=findViewById(R.id.xian_note);
		/**
		 * 各组件注册自定义监听器
		 */
		pagerlister=new MypagerLister();
		viewpager.setOnPageChangeListener(pagerlister);

		relayoutlister=new MyReLayoutLister();
		rep_layout.setOnClickListener(relayoutlister);
		bill_layout.setOnClickListener(relayoutlister);
		setting_layout.setOnClickListener(relayoutlister);
		//account_layout.setOnClickListener(relayoutlister);

		dynamicAddView(text_rep, "textColor", R.color.font_color);
		dynamicAddFontView(text_rep);
		dynamicAddView(text_bill, "textColor", R.color.font_color);
		dynamicAddFontView(text_bill);
		dynamicAddView(text_setting, "textColor", R.color.font_color);
		dynamicAddFontView(text_setting);
	}
	/**
	 * 实例化fragment
	 */
	@Override
	protected void init() {

	}
	private void IniData(){
		fm=getSupportFragmentManager();
		freport = new ReportActivity();
		fbill=new BillActivity();
		fsetting=new SettingActivity();
		//fnotebook=new FgNoteBook();
		list=new ArrayList<Fragment>();
		list.add(freport);
		list.add(fbill);
		list.add(fsetting);
		//list.add(fnotebook);
		viewadapter=new ViewPagerAdapter(fm, list);
		viewpager.setAdapter(viewadapter);
		initState();
	}

	private void initState() {
		text_bill.setTextColor(text_bill.getResources().getColor(R.color.nav_active_color));
		xian_bill.setBackgroundColor(xian_bill.getResources().getColor(R.color.nav_active_color));
		viewpager.setCurrentItem(1);
	}

	private class MypagerLister implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			if(arg0==2){
				int resourceAndVnum=viewpager.getCurrentItem();
				clearChoicked();
				changeChoicked(resourceAndVnum);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

	}


	private  class MyReLayoutLister implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clearChoicked();
			changeChoicked(v.getId());
		}

	}


	private  void clearChoicked(){
		//text_notebook.setTextColor(text_notebook.getResources().getColor(R.color.nav_default_font_color));
		text_rep.setTextColor(text_rep.getResources().getColor(R.color.nav_default_font_color));
		text_bill.setTextColor(text_bill.getResources().getColor(R.color.nav_default_font_color));
		text_setting.setTextColor(text_setting.getResources().getColor(R.color.nav_default_font_color));


		//xian_note.setBackgroundColor(xian_note.getResources().getColor(R.color.nav_default_color));
		xian_rep.setBackgroundColor(xian_rep.getResources().getColor(R.color.nav_default_color));
		xian_bill.setBackgroundColor(xian_bill.getResources().getColor(R.color.nav_default_color));
		xian_setting.setBackgroundColor(xian_setting.getResources().getColor(R.color.nav_default_color));
	}

	public void changeChoicked(int resourceAndVnum) {

		switch (resourceAndVnum) {
			case R.id.rep_layout:
			case 0:
				text_rep.setTextColor(text_rep.getResources().getColor(R.color.nav_active_color));
				xian_rep.setBackgroundColor(xian_rep.getResources().getColor(R.color.nav_active_color));
				viewpager.setCurrentItem(0);
				break;
			case R.id.bill_layout:
			case 1:
				text_bill.setTextColor(text_bill.getResources().getColor(R.color.nav_active_color));
				xian_bill.setBackgroundColor(xian_bill.getResources().getColor(R.color.nav_active_color));
				viewpager.setCurrentItem(1);
				break;
			case R.id.setting_layout:
			case 2:
				text_setting.setTextColor(text_setting.getResources().getColor(R.color.nav_active_color));
				xian_setting.setBackgroundColor(xian_setting.getResources().getColor(R.color.nav_active_color));
				viewpager.setCurrentItem(2);
				break;
			default:
				break;
		}
	}



}
