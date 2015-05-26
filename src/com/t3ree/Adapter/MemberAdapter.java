package com.t3ree.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.t3ree.Activity.ActivityAddBill;
import com.t3ree.Activity.ActivityMemberBillList;
import com.t3ree.Activity.Constants;
import com.t3ree.Adapter.BillAdapter.ViewHolder;
import com.t3ree.Entity.BillEntity;
import com.t3ree.Entity.MemberEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.JSONParseEntityUtils;
import com.t3ree.View.DelMemberAlertDialog;
import com.t3ree.View.Dialog_Del;
import com.xiaominglicai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MemberAdapter extends BaseAdapter {
	private List<MemberEntity> list;
	private Context context;
	private LayoutInflater inflater;
	private int position;
	private int id;
	private String bid;
	private HTTP_USERUtils http_USERUtils;
	private MemberEntity member;
	private Handler handler;

	public static final int DELESUCCESSFUL = 1;

	public MemberAdapter(Context context, List<MemberEntity> list) {
		this.context = context;
		this.list = list;
		inflater = inflater.from(context);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DELESUCCESSFUL:
					MemberAdapter.this.list.remove(member.getPosition());
					MemberAdapter.this.notifyDataSetChanged();
					break;
				default:
					break;
				}
			};
		};
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	public class ViewHolder {
		TextView tv_name;
		RelativeLayout rl_member;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		View view = null;
		ViewHolder holder = null;
		final MemberEntity memberEntity = list.get(position);
		memberEntity.setPosition(position);
		if (convertView == null) {
			view = inflater.inflate(R.layout.member_list, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) view.findViewById(R.id.tv_member_name);
			view.findViewById(R.id.tv_member_total).setVisibility(View.GONE);
			holder.rl_member = (RelativeLayout) view
					.findViewById(R.id.rl_member_item);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_name.setText(memberEntity.getUsername());
		holder.rl_member.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.putExtra("uid", memberEntity.getId());
				intent.putExtra("name", memberEntity.getUsername());
				intent.setClass(context, ActivityMemberBillList.class);
				context.startActivity(intent);
			}
		});
		holder.rl_member.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				member = memberEntity;
				new DelMemberAlertDialog(context, memberEntity.getId(), handler)
						.show();
				return false;
			}
		});
		return view;
	}
}
