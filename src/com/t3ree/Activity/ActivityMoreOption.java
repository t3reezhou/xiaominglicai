package com.t3ree.Activity;

import com.xiaominglicai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMoreOption extends Activity implements OnClickListener {
	private Button bt_top_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_option);
		findView();
	}

	private void findView() {
		// TODO �Զ����ɵķ������
		bt_top_back = (Button) findViewById(R.id.top_back);
		bt_top_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;
		default:
			break;
		}

	}
}
