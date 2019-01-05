package yao.muyuer.dailyrecord.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import yao.muyuer.dailyrecord.R;


public class AddContentActivity extends Activity implements OnClickListener {

	private Button btn_save, btn_back;
	private EditText edit_context;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_content_layout);
		content = getIntent().getStringExtra("content");
		init();

	}

	private void init() {
		edit_context = (EditText) findViewById(R.id.edit_context);
		edit_context.setText(content);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_save.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_save:
				//this.getIntent().putExtra("Content", edit_context.getText());
				Intent intent = new Intent();
				intent.putExtra("content", edit_context.getText().toString());
				this.setResult(5, intent);
				this.finish();
				break;
			case R.id.btn_back:
				this.finish();
				break;
			default:
				break;
		}
	}
}
