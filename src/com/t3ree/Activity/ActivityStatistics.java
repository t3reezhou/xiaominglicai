package com.t3ree.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.t3ree.View.BarChart01View;
import com.t3ree.View.CircleChart02View;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ActivityStatistics extends Activity implements OnClickListener {
	private Button btn_back;
	private Button btn_2List;
	private CircleChart02View cirleChart;
	private BarChart01View barChar;
	private DBManager dbManager;
	private List<BillEntity> list_totle;
	private String time;
	private TextView tv_cost_moth;
	private TextView tv_cost_rate_month;
	private RadioGroup rg_char;
	private RadioGroup rg_time;
	private int count_cost;
	private int count_get;

	private int count_get_cash;
	private int count_cost_cash;
	private int count_deposit_bank;
	private int count_cost_bank;

	private int year;
	private int month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_chart);
		dbManager = new DBManager(this);
		Calendar cl = Calendar.getInstance();
		year = cl.get(Calendar.YEAR);
		month = cl.get(Calendar.MONTH) + 1;
		time = month < 10 ? year + "-0" + month : year + "-" + month;
		list_totle = new ArrayList<BillEntity>();
		findView();
		fillView();
	}

	private void findView() {
		// TODO 自动生成的方法存根
		btn_back = (Button) findViewById(R.id.top_back);
		btn_2List = (Button) findViewById(R.id.top_btn);
		cirleChart = (CircleChart02View) findViewById(R.id.expense_char_circle_view);
		barChar = (BarChart01View) findViewById(R.id.expense_char_bar_view);
		tv_cost_moth = (TextView) findViewById(R.id.expense_chart_current_money_text);
		tv_cost_rate_month = (TextView) findViewById(R.id.expense_chart_current_rate);
		rg_char = (RadioGroup) findViewById(R.id.expense_chart_radio_group_chart_type);
		rg_time = (RadioGroup) findViewById(R.id.rg_time);

		btn_back.setOnClickListener(this);
		btn_2List.setOnClickListener(this);

		rg_char.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				if (checkedId == R.id.expense_chart_pie) {
					cirleChart.setVisibility(View.VISIBLE);
					barChar.setVisibility(View.GONE);
				} else if (checkedId == R.id.expense_chart_histogram) {
					cirleChart.setVisibility(View.GONE);
					barChar.setVisibility(View.VISIBLE);
				} else if (checkedId == R.id.expense_chart_tendency) {

				}
			}
		});
		rg_time.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				if (checkedId == R.id.expense_chart_btn_one_month) {
					time = month < 10 ? year + "-0" + month : year + "-"
							+ month;
					list_totle = dbManager.queryByMonth(time, 0, 3);
				} else if (checkedId == R.id.expense_chart_btn_year) {
					time = year + "";
					list_totle = dbManager.queryByMonth(time, 0, 3);
				} else if (checkedId == R.id.expense_chart_btn_all) {
					list_totle = dbManager.quetyAll();
				}
				count_get = 0;
				count_cost = 0;
				count_get_cash = 0;
				count_cost_cash = 0;
				count_deposit_bank = 0;
				count_cost_bank = 0;
				for (BillEntity bill : list_totle) {
					switch (bill.getType()) {
					case 0:
						count_get += bill.getUsed();
						count_get_cash += bill.getUsed();
						break;
					case 1:
						count_cost += bill.getUsed();
						count_cost_cash += bill.getUsed();
						break;
					case 2:
						count_get += bill.getUsed();
						count_deposit_bank += bill.getUsed();
						break;
					case 3:
						count_cost_bank += bill.getUsed();
					default:
						break;
					}
				}
				if (count_get != 0) {
					cirleChart.setPercentage((count_cost * 100) / count_get);
				} else {
					cirleChart.setPercentage(0);
				}
				cirleChart.refreshChart();
				barChar.setPercentage(new int[] { count_get_cash,
						count_cost_cash, count_deposit_bank, count_cost_bank });
				barChar.refreshChart();
			}
		});
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		dbManager = new DBManager(this);
		list_totle = new ArrayList<BillEntity>();
		list_totle = dbManager.queryByMonth(time, 0, 2);
		for (BillEntity bill : list_totle) {
			switch (bill.getType()) {
			case 0:
				count_get += bill.getUsed();
				count_get_cash += bill.getUsed();
				break;
			case 1:
				count_cost += bill.getUsed();
				count_cost_cash += bill.getUsed();
				break;
			case 2:
				count_get += bill.getUsed();
				count_deposit_bank += bill.getUsed();
				break;
			case 3:
				count_cost_bank += bill.getUsed();
			default:
				break;
			}
		}
		tv_cost_moth.setText(count_cost + "");
		if (count_get != 0) {
			tv_cost_rate_month.setText((count_cost * 100) / count_get + "%");
			cirleChart.setPercentage((count_cost * 100) / count_get);
		} else {
			tv_cost_rate_month.setText(0 + "%");
			cirleChart.setPercentage(0);
		}
		barChar.setPercentage(new int[] { count_get_cash, count_cost_cash,
				count_deposit_bank, count_cost_bank });
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			Intent intent = new Intent();
			intent.setClass(this, ActivityListShow.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
