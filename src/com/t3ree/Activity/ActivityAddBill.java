package com.t3ree.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amo.demo.wheelview.PowerHallDateDialog;
import com.t3ree.Adapter.BillAdapter;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddBill extends Activity implements OnClickListener {
	private Button btn_back;
	private Button btn_save_top;
	// private Button btn_saveAndNext;
	private TextView tv_title;
	private TextView tv_edit_record_money;
	private TextView tv_edit_record_expense_type;
	private TextView tv_edit_record_time;
	private TextView tv_edit_record_remark;
	private RelativeLayout rl_edit_record_time;

	private InputMethodManager manager;
	private DBManager dbManager;
	private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;// popupwindow
	private ListView lvPopupList;// popupwindow�е�ListView
	private int NUM_OF_VISIBLE_LIST_ROWS = 3;// ָ��popupwindow��Item������

	private int choosenType = 0;

	private int activityType = 0;
	private int id;
	private int position;
	private String bid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_record);
		dbManager = new DBManager(this);
		findView();
		fillView();
		iniData();
		iniPopupWindow();
	}

	private void iniData() {
		// TODO �Զ����ɵķ������
		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("share_key", "�ֽ�����");
		moreList.add(map);
		map = new HashMap<String, String>();
		map.put("share_key", "�ֽ�֧��");
		moreList.add(map);
		map = new HashMap<String, String>();
		map.put("share_key", "���д���");
		moreList.add(map);
		map = new HashMap<String, String>();
		map.put("share_key", "����ȡ��");
		moreList.add(map);
	}

	private void iniPopupWindow() {
		// TODO �Զ����ɵķ������

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.task_detail_popupwindow, null);
		lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);
		pwMyPopWindow = new PopupWindow(layout);
		pwMyPopWindow.setFocusable(true);// �������popupwindow�е�ListView�ſ��Խ��յ���¼�

		lvPopupList.setAdapter(new SimpleAdapter(ActivityAddBill.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(ActivityAddBill.this,
						moreList.get(position).get("share_key"),
						Toast.LENGTH_LONG).show();
				tv_edit_record_expense_type.setText(moreList.get(position).get(
						"share_key"));
				choosenType = position;
				pwMyPopWindow.dismiss();
			}
		});

		// ����popupwindow�Ŀ�Ⱥ͸߶�����Ӧ
		lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		pwMyPopWindow.setWidth(lvPopupList.getMeasuredWidth());
		pwMyPopWindow.setHeight((lvPopupList.getMeasuredHeight() + 20)
				* NUM_OF_VISIBLE_LIST_ROWS);

		// ����popupwindow�����Ļ�����ط���ʧ
		pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.main_bg));// ���ñ���ͼƬ�������ڲ��������ã�Ҫͨ������������
		pwMyPopWindow.setOutsideTouchable(true);
	}

	private void findView() {
		// TODO �Զ����ɵķ������
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		btn_back = (Button) findViewById(R.id.top_back);
		btn_save_top = (Button) findViewById(R.id.top_btn);
		// btn_saveAndNext = (Button) findViewById(R.id.edit_record_btn_again);
		tv_title = (TextView) findViewById(R.id.top_title);
		tv_edit_record_money = (TextView) findViewById(R.id.edit_record_money);
		tv_edit_record_expense_type = (TextView) findViewById(R.id.edit_record_expense_type);
		tv_edit_record_time = (TextView) findViewById(R.id.edit_record_time);
		tv_edit_record_remark = (TextView) findViewById(R.id.edit_record_remark);
		rl_edit_record_time = (RelativeLayout) findViewById(R.id.rl_edit_record_time);

		btn_back.setOnClickListener(this);
		btn_save_top.setOnClickListener(this);
		tv_edit_record_expense_type.setOnClickListener(this);
		rl_edit_record_time.setOnClickListener(this);
	}

	private void fillView() {
		// TODO �Զ����ɵķ������
		Intent getIntent = getIntent();
		Bundle bundle = getIntent.getExtras();
		try {
			if (!bundle.isEmpty())
				activityType = 1;
			id = bundle.getInt("id");
			bid = bundle.getString("bid");
			position = bundle.getInt("position");
			tv_title.setText(bundle.getString("title"));
			tv_edit_record_money.setText(bundle.getFloat("used") + "");
			choosenType = bundle.getInt("type");
			switch (choosenType) {
			case 0:
				tv_edit_record_expense_type.setText("����");
				break;
			case 1:
				tv_edit_record_expense_type.setText("֧��");
				break;
			case 2:
				tv_edit_record_expense_type.setText("���д���");
				break;
			case 3:
				tv_edit_record_expense_type.setText("����֧��");
				break;
			default:
				break;
			}
			tv_edit_record_time.setText(bundle.getString("date"));
			tv_edit_record_remark.setText(bundle.getString("other"));
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;
		int day = cl.get(Calendar.DATE);
		String date = day < 10 ? "0" + day : day + "";
		String time = month < 10 ? year + "-0" + month + "-" + date : year
				+ "-" + month + "-" + date;
		tv_edit_record_time.setText(time);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO �Զ����ɵķ������
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			String year = ((String) tv_edit_record_time.getText()).substring(0,
					4);
			String mth = tv_edit_record_time.getText().toString()
					.substring(5, 7);
			String day = tv_edit_record_time.getText().toString()
					.substring(8, 10);
			String billMoney = tv_edit_record_money.getText().toString()
					.equals("") ? "0" : tv_edit_record_money.getText()
					.toString();
			BillEntity billEntity = new BillEntity(choosenType,
					Float.valueOf(billMoney), tv_edit_record_remark.getText()
							.toString(), year + "-" + mth + "-" + day);
			if (activityType == 0)
				dbManager.addBill(billEntity);
			else if (activityType == 1) {
				billEntity.setId(id);
				billEntity.setBid(bid);
				billEntity.setPosition(position);
				billEntity.setSynchronization(0);
				dbManager.updateBill(billEntity);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("update", billEntity);
				intent.putExtras(bundle);
				setResult(BillAdapter.BILLADAPTER_UPDATEBILL, intent);
			}
			finish();
			break;
		case R.id.edit_record_expense_type:
			pwMyPopWindow.showAsDropDown(tv_edit_record_expense_type, 400, 0);
			break;
		case R.id.rl_edit_record_time:
			PowerHallDateDialog.getDateDialog(this, tv_edit_record_time, 0)
					.show();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		dbManager.closeDB();
	}
}
