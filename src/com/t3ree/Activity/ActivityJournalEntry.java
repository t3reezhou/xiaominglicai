package com.t3ree.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.t3ree.Adapter.BillAdapter;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.xiaominglicai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActivityJournalEntry extends Activity implements OnClickListener {
	private Button btn_back;
	private RadioGroup rg_time;
	private int year;
	private int month;
	private String time;
	private List<BillEntity> list_totle;
	private DBManager dbManager;
	private ListView lv_bills;
	private BillAdapter billAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journal_entry);
		list_totle = new ArrayList<BillEntity>();
		dbManager = new DBManager(this);
		findView();
		fillView();
	}

	private void findView() {
		// TODO 自动生成的方法存根
		btn_back = (Button) findViewById(R.id.top_back);
		rg_time = (RadioGroup) findViewById(R.id.rg_time);
		lv_bills = (ListView) findViewById(R.id.list_view);

		btn_back.setOnClickListener(this);
		rg_time.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				switch (checkedId) {
				case R.id.rb_entry_one_month:
					time = month < 10 ? year + "-0" + month : year + "-"
							+ month;
					list_totle = dbManager.queryByMonth(time, 0, 3);
					break;
				case R.id.rb_entry_year:
					time = year + "";
					list_totle = dbManager.queryByMonth(time, 0, 3);
					break;
				case R.id.rb_entry_all:
					list_totle = dbManager.quetyAll();
					break;
				default:
					break;
				}
				Collections.reverse(list_totle);
				billAdapter = new BillAdapter(ActivityJournalEntry.this,
						list_totle);
				lv_bills.setAdapter(billAdapter);
			}
		});
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		Calendar cl = Calendar.getInstance();
		year = cl.get(Calendar.YEAR);
		month = cl.get(Calendar.MONTH) + 1;
		time = month < 10 ? year + "-0" + month : year + "-" + month;
		list_totle = dbManager.queryByMonth(time, 0, 3);
		billAdapter = new BillAdapter(ActivityJournalEntry.this, list_totle);
		lv_bills.setAdapter(billAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.closeDB();
	}
}
