package com.t3ree.Activity;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;

import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.t3ree.Utils.UpLoadAsyncTask;
import com.t3ree.View.LogoutAlertDialog;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends Activity implements OnClickListener {
	private Button btn_add;
	private Button btn_family;
	private Button btn_liushui;
	private Button btn_jiben;
	private Button btn_yinhang;
	private Button btn_tongji;
	private Button btn_top;

	private TextView tv_main_top_panel_month;
	private TextView tv_main_top_panel_spend_text;

	private TextView tv_main_btn_login;
	private TextView tv_main_btn_sign;
	private TextView tv_main_btn_tongbu;
	private TextView tv_main_btn_logout;
	private TextView tv_main_username;

	private DBManager dbManager;
	private String time;

	private LinearLayout ll_main_beforlogin;
	private LinearLayout ll_main_afterlogin;

	private SharedPreferences sp;
	private Editor editor;

	private UpLoadAsyncTask upLoadAsyncTask;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dbManager = new DBManager(this);
		sp = getSharedPreferences("core_user", MODE_PRIVATE);
		findView();
		fillView();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				totle();
			}
		};
	}

	private void findView() {
		// TODO 自动生成的方法存根
		btn_add = (Button) findViewById(R.id.main_btn_add);
		btn_family = (Button) findViewById(R.id.main_btn_jiatingzu);
		btn_liushui = (Button) findViewById(R.id.main_btn_liushui);
		btn_jiben = (Button) findViewById(R.id.main_btn_jiben);
		btn_yinhang = (Button) findViewById(R.id.main_btn_yinhang);
		btn_tongji = (Button) findViewById(R.id.main_btn_tongji);
		btn_top = (Button) findViewById(R.id.top_btn);
		tv_main_top_panel_month = (TextView) findViewById(R.id.main_top_panel_month);
		tv_main_top_panel_spend_text = (TextView) findViewById(R.id.main_top_panel_spend_text);

		tv_main_btn_login = (TextView) findViewById(R.id.tv_main_btn_login);
		tv_main_btn_sign = (TextView) findViewById(R.id.tv_main_btn_sign);
		tv_main_btn_tongbu = (TextView) findViewById(R.id.tv_main_btn_tongbu);
		tv_main_btn_logout = (TextView) findViewById(R.id.tv_main_btn_logout);
		tv_main_username = (TextView) findViewById(R.id.tv_main_username);

		ll_main_beforlogin = (LinearLayout) findViewById(R.id.ll_main_beforlogin);
		ll_main_afterlogin = (LinearLayout) findViewById(R.id.ll_main_afterlogin);

		btn_add.setOnClickListener(this);
		btn_family.setOnClickListener(this);
		btn_liushui.setOnClickListener(this);
		btn_jiben.setOnClickListener(this);
		btn_yinhang.setOnClickListener(this);
		btn_tongji.setOnClickListener(this);
		btn_top.setOnClickListener(this);

		tv_main_btn_login.setOnClickListener(this);
		tv_main_btn_sign.setOnClickListener(this);
		tv_main_btn_tongbu.setOnClickListener(this);
		tv_main_btn_logout.setOnClickListener(this);
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;
		time = month < 10 ? year + "-0" + month : year + "-" + month;
		tv_main_top_panel_month.setText(time);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Intent intent = null;
		switch (v.getId()) {
		case R.id.main_btn_add:
			intent = new Intent();
			intent.setClass(this, ActivityAddBill.class);
			startActivity(intent);
			break;
		case R.id.main_btn_jiatingzu:
			intent = new Intent();
			intent.setClass(this, ActivityFamilyList.class);
			startActivity(intent);
			break;
		case R.id.main_btn_liushui:
			intent = new Intent();
			intent.setClass(this, ActivityJournalEntry.class);
			startActivity(intent);
			break;
		case R.id.main_btn_jiben:
			intent = new Intent();
			intent.putExtra("activityType", 0);
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			break;
		case R.id.main_btn_yinhang:
			intent = new Intent();
			intent.putExtra("activityType", 1);
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			break;
		case R.id.main_btn_tongji:
			intent = new Intent();
			intent.setClass(this, ActivityStatistics.class);
			startActivity(intent);
			break;
		case R.id.top_btn:
			intent = new Intent();
			intent.setClass(this, ActivityMoreOption.class);
			startActivity(intent);
			break;
		case R.id.tv_main_btn_login:
			intent = new Intent();
			intent.setClass(this, ActivityLogin.class);
			startActivity(intent);
			break;
		case R.id.tv_main_btn_sign:
			intent = new Intent();
			intent.setClass(this, ActivitySignUp.class);
			startActivity(intent);
			break;
		case R.id.tv_main_btn_tongbu:
			if ("".equals(Constants.username) || "".equals(Constants.password)) {

			} else {
				try {
					upLoadAsyncTask = new UpLoadAsyncTask(this, handler);
					upLoadAsyncTask.execute(1);
				} catch (MalformedURLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				// totle();
			}
			break;
		case R.id.tv_main_btn_logout:
			if (ll_main_afterlogin.getVisibility() == View.VISIBLE) {
				ll_main_afterlogin.setVisibility(View.GONE);
				ll_main_beforlogin.setVisibility(View.VISIBLE);
			}
			totle();
			if (dbManager.queryBySynchronization().size() > 0) {
				new LogoutAlertDialog(this, handler, dbManager).show();
			} else {
				Constants.username = "";
				Constants.password = "";
				dbManager.delAll();
				totle();
			}
			tv_main_username.setText("未登录");
			editor = sp.edit();
			editor.remove("username");
			editor.remove("password");
			editor.commit();
			Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		List<BillEntity> bills = dbManager.queryByMonth(time, 0, 3);
		float totle = 0;
		totle();

		Constants.username = sp.getString("username", "");
		Constants.password = sp.getString("password", "");
		Constants.uid = sp.getString("uid", "");

		if (Constants.username != null && !"".equals(Constants.username)) {
			tv_main_username.setText("欢迎  " + Constants.username);
			if (ll_main_beforlogin.getVisibility() == View.VISIBLE) {
				ll_main_beforlogin.setVisibility(View.GONE);
				ll_main_afterlogin.setVisibility(View.VISIBLE);
			}
		}
	}

	private void totle() {
		List<BillEntity> bills = dbManager.queryByMonth(time, 0, 3);
		float totle = 0;
		for (BillEntity billEntity : bills) {
			if (billEntity.getType() == 0 || billEntity.getType() == 2)
				totle += Float.valueOf(billEntity.getUsed());
			else
				totle -= Float.valueOf(billEntity.getUsed());
		}
		tv_main_top_panel_spend_text.setText(String.valueOf(totle));
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		dbManager.closeDB();
	}

}
