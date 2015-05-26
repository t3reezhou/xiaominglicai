package com.t3ree.Adapter;

import java.util.List;

import com.t3ree.Activity.ActivityAddBill;
import com.t3ree.Activity.ActivityListShow;
import com.t3ree.Activity.Constants;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.View.Dialog_Del;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BillAdapter extends BaseAdapter implements DataListener {
	public static final int BILLADAPTER_UPDATEBILL = 0;
	private List<BillEntity> list;
	private Context context;
	private LayoutInflater inflater;
	private int position;
	private int id;
	private String bid;
	private HTTP_USERUtils http_USERUtils;

	public BillAdapter(Context context, List<BillEntity> list) {
		this.list = list;
		this.context = context;
		this.http_USERUtils = new HTTP_USERUtils(this);
		inflater = inflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	public class ViewHolder {
		TextView tv_type;
		TextView tv_used;
		TextView tv_time;
		// LinearLayout ll_keyboard;
		RelativeLayout rl_bill;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		View view = null;
		ViewHolder holder = null;
		final BillEntity billEntity = list.get(position);
		billEntity.setPosition(position);
		if (convertView == null) {
			view = inflater.inflate(R.layout.expense_list_item, null);
			holder = new ViewHolder();
			// holder.ll_keyboard = (LinearLayout) view
			// .findViewById(R.id.ll_item_keyboard);
			holder.tv_type = (TextView) view.findViewById(R.id.line_text1);
			holder.tv_time = (TextView) view.findViewById(R.id.line_text2);
			holder.tv_used = (TextView) view.findViewById(R.id.line_text3);
			holder.rl_bill = (RelativeLayout) view.findViewById(R.id.rl_bill);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		switch (billEntity.getType()) {
		case 0:
			holder.tv_type.setText("现金收入");
			break;
		case 1:
			holder.tv_type.setText("现金支出");
			break;
		case 2:
			holder.tv_type.setText("银行存入");
			break;
		case 3:
			holder.tv_type.setText("银行取出");
		default:
			break;
		}
		// holder.tv_type.setText(String.valueOf(billEntity.getType()));
		holder.tv_time.setText(String.valueOf(billEntity.getDate()));
		holder.tv_used.setText(String.valueOf(billEntity.getUsed()));
		// holder.ll_keyboard.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO 自动生成的方法存根
		// Uri uri = Uri.parse("tel:" + billEntity.getPhone());
		// Intent intent = new Intent(Intent.ACTION_CALL, uri);
		// context.startActivity(intent);
		// }
		// });
		holder.rl_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(context, billEntity.getType() + "",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(context, ActivityAddBill.class);
				Bundle bundle = new Bundle();
				String title = "修改收支记录";
				bundle.putString("title", title);
				bundle.putInt("id", billEntity.getId());
				bundle.putString("bid", billEntity.getBid());
				bundle.putFloat("used", billEntity.getUsed());
				bundle.putInt("type", billEntity.getType());
				bundle.putString("other", billEntity.getOther());
				bundle.putString("date", billEntity.getDate());
				bundle.putInt("position", billEntity.getPosition());
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent,
						BILLADAPTER_UPDATEBILL);
				// 这里采用startActivityForResult来做跳转，此处的0为一个依据，可以写其他的值，
				// 但一定要>=0
			}
		});

		holder.rl_bill.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				BillAdapter.this.position = billEntity.getPosition();
				id = billEntity.getId();
				bid = billEntity.getBid();
				Dialog_Del dialog = new Dialog_Del(context, BillAdapter.this);
				dialog.show();
				return false;
			}
		});
		return view;
	}

	@Override
	public void resultBack(Message msg) {
		// TODO 自动生成的方法存根
		DBManager mgr;
		switch (msg.what) {
		case Dialog_Del.DELETE_BILL_LOCAL:
			// list.remove(position);
			// this.notifyDataSetChanged();
			mgr = new DBManager(context);
			mgr.delBill(id);
			break;
		case Dialog_Del.DELETE_BILL_ONLINE:
			if (bid != null && !"".equals(bid))
				http_USERUtils.delBill(bid);
			else
				Toast.makeText(context, "该数据未与后台同步过", Toast.LENGTH_SHORT)
						.show();
			break;
		case Constants.delBill:
			mgr = new DBManager(context);
			mgr.delBill(id);
			list.remove(position);
			this.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}
}
