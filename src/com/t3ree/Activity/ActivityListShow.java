package com.t3ree.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.amo.demo.wheelview.PowerHallDateDialog;
import com.t3ree.Adapter.BillAdapter;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityListShow extends Activity implements OnClickListener {
	private Button btn_back;
	private Button btn_2char;
	private Button btn_previous;
	private Button btn_next;
	private TextView tv_top_title;
	private TextView tv_expense_list_month;
	private TextView tv_expense_list_money;
	private LinearLayout ll_expense_list_popup;
	private RelativeLayout rl_top_title_parent;
	private ListView lv_bill;
	private DBManager dbManager;
	private BillAdapter billAdapter;
	private List<BillEntity> list;

	private int activityType = 0;
	private int start = 0;
	private int end = 1;
	private Float totle = 0.0f;

	private Button bt_main_liushui;
	private Button bt_main_jiben;
	private Button bt_main_yinhang;
	private Button bt_main_tongji;

	private static final String TAG = "LifeCycleActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_list);
		findView();
		fillView();
	}

	private void findView() {
		// TODO 自动生成的方法存根
		btn_back = (Button) findViewById(R.id.top_back);
		btn_2char = (Button) findViewById(R.id.top_btn);
		btn_previous = (Button) findViewById(R.id.expense_list_btn_left);
		btn_next = (Button) findViewById(R.id.expense_list_btn_right);

		tv_top_title = (TextView) findViewById(R.id.top_title);
		tv_expense_list_month = (TextView) findViewById(R.id.expense_list_month);
		tv_expense_list_money = (TextView) findViewById(R.id.expense_list_money);

		ll_expense_list_popup = (LinearLayout) findViewById(R.id.expense_list_popup);

		rl_top_title_parent = (RelativeLayout) findViewById(R.id.top_title_parent);

		lv_bill = (ListView) findViewById(R.id.my_list_view);

		bt_main_liushui = (Button) findViewById(R.id.main_btn_liushui);
		bt_main_jiben = (Button) findViewById(R.id.main_btn_jiben);
		bt_main_yinhang = (Button) findViewById(R.id.main_btn_yinhang);
		bt_main_tongji = (Button) findViewById(R.id.main_btn_tongji);

		tv_expense_list_month.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_2char.setOnClickListener(this);
		btn_previous.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		rl_top_title_parent.setOnClickListener(this);

		bt_main_liushui.setOnClickListener(this);
		bt_main_jiben.setOnClickListener(this);
		bt_main_yinhang.setOnClickListener(this);
		bt_main_tongji.setOnClickListener(this);
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null)
			activityType = bundle.getInt("activityType");

		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;
		String time = month < 10 ? year + "-0" + month : year + "-" + month;

		switch (activityType) {
		case 0:
			tv_top_title.setText("基本");
			start = 0;
			end = 1;
			break;
		case 1:
			tv_top_title.setText("银行");
			start = 2;
			end = 3;
			break;
		default:
			break;
		}
		dbManager = new DBManager(this);
		list = new ArrayList<BillEntity>();
		list = dbManager.queryByMonth(time, start, end);
		Collections.reverse(list);

		billAdapter = new BillAdapter(this, list);
		lv_bill.setAdapter(billAdapter);
		tv_expense_list_month.setText(time);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		String time;
		int month = 0;
		int year = 0;
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
		case R.id.top_title_parent:
			if (ll_expense_list_popup.getVisibility() == View.GONE)
				ll_expense_list_popup.setVisibility(View.VISIBLE);
			else
				ll_expense_list_popup.setVisibility(View.GONE);
			break;
		case R.id.expense_list_btn_left:
			time = tv_expense_list_month.getText().toString().trim();
			time = new String(changeTime(time, year, month, 0));
			changeList(time);
			countTotal();
			break;
		case R.id.expense_list_btn_right:
			time = tv_expense_list_month.getText().toString().trim();
			time = new String(changeTime(time, year, month, 1));
			changeList(time);
			countTotal();
			break;
		case R.id.expense_list_month:
			PowerHallDateDialog.getDateDialog(this, tv_expense_list_month, 1)
					.show();
			break;
		case R.id.main_btn_liushui:
			if (ll_expense_list_popup.getVisibility() == View.VISIBLE)
				ll_expense_list_popup.setVisibility(View.GONE);
			intent = new Intent();
			intent.setClass(this, ActivityJournalEntry.class);
			startActivity(intent);
			break;
		case R.id.main_btn_jiben:
			if (ll_expense_list_popup.getVisibility() == View.VISIBLE)
				ll_expense_list_popup.setVisibility(View.GONE);
			intent = new Intent();
			intent.putExtra("activityType", 0);
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			break;
		case R.id.main_btn_yinhang:
			if (ll_expense_list_popup.getVisibility() == View.VISIBLE)
				ll_expense_list_popup.setVisibility(View.GONE);
			intent = new Intent();
			intent.putExtra("activityType", 1);
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			break;
		case R.id.main_btn_tongji:
			if (ll_expense_list_popup.getVisibility() == View.VISIBLE)
				ll_expense_list_popup.setVisibility(View.GONE);
			intent = new Intent();
			intent.setClass(this, ActivityStatistics.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == BillAdapter.BILLADAPTER_UPDATEBILL) {
			try {
				BillEntity billEntity = (BillEntity) data
						.getSerializableExtra("update");
				list.remove(billEntity.getPosition());
				list.add(billEntity.getPosition(), billEntity);
				billAdapter.notifyDataSetChanged();
				countTotal();
			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	private String changeTime(String time, int year, int month, int type) {
		year = Integer.valueOf(time.substring(0, time.indexOf("-")));
		month = Integer.valueOf(time.substring(time.indexOf("-") + 1,
				time.length()));
		switch (type) {
		case 0:
			if (month == 1) {
				month = 12;
				year--;
			} else
				month--;
			break;
		case 1:
			if (month == 12) {
				month = 1;
				year++;
			} else
				month++;
		default:
			break;
		}
		time = (month < 10) ? year + "-0" + month : year + "-" + month;
		tv_expense_list_month.setText(time);
		return time;
	}

	private void changeList(String time) {
		list.removeAll(list);
		list.addAll(dbManager.queryByMonth(time, start, end));
		Collections.reverse(list);
		billAdapter.notifyDataSetChanged();
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

	// Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.i(TAG, "onWindowFocusChanged called.");
		String time = tv_expense_list_month.getText().toString().trim();
		changeList(time);
		countTotal();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (ll_expense_list_popup.getVisibility() == View.VISIBLE)
				ll_expense_list_popup.setVisibility(View.GONE);

		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		Log.i(TAG, "onDestroy called.");
		dbManager.closeDB();
	}
}
