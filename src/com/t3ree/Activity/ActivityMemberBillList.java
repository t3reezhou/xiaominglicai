package com.t3ree.Activity;

import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.Adapter.BillAdapter;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.JSONParseEntityUtils;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMemberBillList extends Activity implements
		OnClickListener, DataListener {
	private Button btn_back;
	private Button btn_2char;
	private TextView tv_expense_list_money;
	private RelativeLayout rl_top_title_parent;
	private RelativeLayout rl_nothing;
	private ListView lv_bill;
	private DBManager dbManager;
	private BillAdapter billAdapter;
	private List<BillEntity> list;

	private Float totle = 0.0f;
	private HTTP_USERUtils http_USERUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_list);
		findView();
		fillView();
	}

	private void findView() {
		// TODO �Զ����ɵķ������
		btn_back = (Button) findViewById(R.id.top_back);
		btn_2char = (Button) findViewById(R.id.top_btn);

		tv_expense_list_money = (TextView) findViewById(R.id.expense_list_money);

		rl_top_title_parent = (RelativeLayout) findViewById(R.id.top_title_parent);
		rl_nothing = (RelativeLayout) findViewById(R.id.expense_list_rl);

		lv_bill = (ListView) findViewById(R.id.my_list_view);

		btn_back.setOnClickListener(this);
		btn_2char.setOnClickListener(this);
		rl_top_title_parent.setOnClickListener(this);
	}

	private void fillView() {
		// TODO �Զ����ɵķ������
		findViewById(R.id.top_btn).setVisibility(View.GONE);
		Intent intent = getIntent();
		String uid = intent.getStringExtra("uid");
		rl_nothing.setVisibility(View.GONE);
		rl_top_title_parent.setVisibility(View.GONE);
		if (intent.getStringExtra("name").equals(Constants.username)) {
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			this.finish();
		} else {
			http_USERUtils = new HTTP_USERUtils(this);
			http_USERUtils.queryBillByManager(Constants.fid, uid);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent;
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			intent = new Intent();
			intent.setClass(this, ActivityStatistics.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public void countTotal() {
		totle = (float) 0;
		for (BillEntity billEntity : list) {
			if (billEntity.getType() == 0 || billEntity.getType() == 2)
				totle += Float.valueOf(billEntity.getUsed());
			else
				totle -= Float.valueOf(billEntity.getUsed());
		}
		tv_expense_list_money.setText(String.valueOf(totle));
	}

	@Override
	public void resultBack(Message msg) {
		// TODO �Զ����ɵķ������
		switch (msg.what) {
		case Constants.queryBillByManager:
			String error = null;
			try {
				error = ErrorUtils.error(Integer.valueOf(new JSONObject(msg.obj
						.toString()).getString("error")));
			} catch (NumberFormatException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			if (error != null && !"".equals(error)) {
				Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				this.finish();
			} else if (msg.obj != null && !"\"\"".equals(msg.obj)) {
				List<BillEntity> bills = (List<BillEntity>) JSONParseEntityUtils
						.parserBillList(msg.obj.toString())[0];
				Collections.reverse(bills);
				billAdapter = new BillAdapter(this, bills);
				lv_bill.setAdapter(billAdapter);
			}
			break;
		default:
			break;
		}
	}
}
