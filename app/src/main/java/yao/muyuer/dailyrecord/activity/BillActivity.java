package yao.muyuer.dailyrecord.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.adapter.ListViewAdapterOfBill;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.library.other.WaveProgressView;
import yao.muyuer.library.other.WaveView;

public class BillActivity extends BaseFragment implements OnClickListener {
	private Button btn_bill_date; //add_btn_of_bill,
	private View view;
	private TextView bill_left_show_text,bill_right_show_text;
//	private WaveView bnp;
	private WaveProgressView bnp;
	private FloatingActionButton fab_add;
	/**
	 * adapter相关的变量
	 */
	private ListView billlistview;
	private BillItemOnClick billItemOnClick;
	private GridViewEntity ent;
	private ListViewAdapterOfBill adapter;

	/**
	 * 获得数据库bill数据相关变量
	 */
	private UserService service;
	private Integer[] icons1;
	private Integer[] icons2;

	private Integer[] img0 = { R.drawable.type_big_0};
	private List<GridViewEntity> dblist;

	@Override
	public void onStart() {
		super.onStart();
		try {
			initView();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Override
	protected int setLayoutResourceID() {
		return R.layout.bill_layout;
	}
	@Override
	protected void setUpView() {
		btn_bill_date= $(R.id.btn_bill_date);
		btn_bill_date.setOnClickListener(this);
		billlistview = $(R.id.billlistview);
		bill_left_show_text= $(R.id.bill_left_show_text);
		bill_right_show_text= $(R.id.bill_right_show_text);
		bnp = $(R.id.ns_bar);
		fab_add = $(R.id.fab_add);
		fab_add.setOnClickListener(this);

		try {
			initView();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dynamicAddView(bill_left_show_text, "textColor", R.color.font_color);
		dynamicAddFontView(bill_left_show_text);
		dynamicAddView(bill_right_show_text, "textColor", R.color.font_color);
		dynamicAddFontView(bill_right_show_text);
	}

	private void initAmountType(){
		icons1 = service.GetAmountTypeIcon(1);
		icons2 = service.GetAmountTypeIcon(2);
	}

	private void initView() throws ParseException {
		billItemOnClick=new BillItemOnClick();
		billlistview.setOnItemClickListener(billItemOnClick);
		service = new UserService(this.getActivity());
		initAmountType();
		dblist=new ArrayList<GridViewEntity>() ;
		dblist = service.findAllBill();
		for (GridViewEntity dbent : dblist) {
			int state= dbent.getAmountType();
			if(state==0){
				dbent.setIcon(icons1[dbent.getIcon()]);
			}else if(state==1){
				dbent.setIcon(icons2[dbent.getIcon()]);
			}
		}
		adapter = new ListViewAdapterOfBill(getActivity(), dblist);
		billlistview.setAdapter(adapter);
		refData();
	}
	private void refData()
	{
		Calendar now = Calendar.getInstance();
		double sy=service.findBillMoney(1);
		double zc=service.findBillMoney(0);
		bill_left_show_text.setText(Calendar.MONTH+"月收入 \n" +
				sy);
		bill_right_show_text.setText(Calendar.MONTH+"月支出\n" +
				zc);
		DecimalFormat df = new DecimalFormat("#.00");
		double b = Math.rint((zc/(sy+0.00))*100);
		Integer bl = (int)(100-b);
		if (bl < 0)
			bl = 0;
		bnp.setCurrent(bl, bl + "%");//.setFlowNum(bl);
	}
	private class BillItemOnClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			ent = dblist.get(position);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fab_add:
				Intent intent = new Intent(getActivity(), AddBillActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.btn_bill_date:
				Intent intent2 = new Intent(getActivity(), CalendarActivity.class);
				getActivity().startActivity(intent2);
				break;
			default:
				break;
		}
	}

}
