package yao.muyuer.dailyrecord.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.adapter.ListViewAdapterOfDialogActivity;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;

public class SelAccountDialogActivity extends Activity {

	private ListViewAdapterOfDialogActivity adapter;
	private List<GridViewEntity> list;
	private UserService service;
	
	/**
	 * 定义DialogActivity布局相关的变量
	 */
	private ListView listview_of_diact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sel_account_dialog_layout);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		initView();
		init();
		listview_of_diact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stu
				String accountName = list.get(position).getAccountName();
				Integer accountId = list.get(position).getAccountId();
				//Toast.makeText(DialogActivity.this, st, Toast.LENGTH_SHORT).show();
				SelAccountDialogActivity.this.getIntent().putExtra("AccountName", accountName);
				SelAccountDialogActivity.this.getIntent().putExtra("AccountId", accountId);
				SelAccountDialogActivity.this.setResult(RESULT_OK, SelAccountDialogActivity.this.getIntent());
				SelAccountDialogActivity.this.finish();
			}
		});

	}
	private void initView(){
		listview_of_diact=(ListView)findViewById(R.id.listview_of_diact);
	}
	/**
	 * 实例化adapter相关的
	 */
	private void init(){
		service=new UserService(SelAccountDialogActivity.this);
		list=service.findAllAccount();
		
		adapter=new ListViewAdapterOfDialogActivity(SelAccountDialogActivity.this, list);
		listview_of_diact.setAdapter(adapter);
	}
	
}
