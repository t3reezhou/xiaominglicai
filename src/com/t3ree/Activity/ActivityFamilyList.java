package com.t3ree.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.Adapter.MemberAdapter;
import com.t3ree.Entity.MemberEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.JSONParseEntityUtils;
import com.xiaominglicai.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActivityFamilyList extends Activity implements OnClickListener,
		DataListener {
	private HTTP_USERUtils http_USERUtils;
	private ProgressDialog progressDialog;
	private RelativeLayout rl_add;
	private RelativeLayout rl_join;
	private ListView lv_member;
	private MemberAdapter memberAdapter;

	private Button bt_back;
	private Button bt_quit;
	private Button bt_add;
	private List<MemberEntity> memberEntities;
	private List<Integer> memberWhich;

	// private

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.family_list);
		memberWhich = new ArrayList<Integer>();
		http_USERUtils = new HTTP_USERUtils(this);
		progressDialog = new ProgressDialog(this);
		iniDialog();
		findView();
		// fillView();
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		fillView();
	}

	private void findView() {
		// TODO 自动生成的方法存根
		rl_add = (RelativeLayout) findViewById(R.id.rl_family_list_addfamily);
		rl_join = (RelativeLayout) findViewById(R.id.rl_family_list_joinfamily);
		lv_member = (ListView) findViewById(R.id.list_view);
		bt_back = (Button) findViewById(R.id.top_back);
		bt_quit = (Button) findViewById(R.id.top_btn);
		bt_add = (Button) findViewById(R.id.btn_add_manager);

		bt_back.setOnClickListener(this);
		bt_quit.setOnClickListener(this);
		rl_add.setOnClickListener(this);
		rl_join.setOnClickListener(this);
		bt_add.setOnClickListener(this);
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		progressDialog.show();
		http_USERUtils.queryFamiliesMyself();
	}

	private void iniDialog() {
		// TODO 自动生成的方法存根
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch (v.getId()) {
		case R.id.rl_family_list_addfamily:
			intent = new Intent();
			intent.putExtra("type", 1);
			intent.setClass(this, ActivityFamilyEdit.class);
			startActivity(intent);
			break;
		case R.id.rl_family_list_joinfamily:
			intent = new Intent();
			intent.putExtra("type", 2);
			intent.setClass(this, ActivityFamilyEdit.class);
			startActivity(intent);
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			progressDialog.show();
			http_USERUtils.delMember(Constants.fid, Constants.uid);
			break;
		case R.id.btn_add_manager:
			final String[] items = new String[memberEntities.size()];

			// for (int i = 0; i < memberEntities.size(); i++) {
			// items[i] = memberEntities.get(i).getUsername();
			// }
			// new AlertDialog.Builder(this)
			// .setMultiChoiceItems(items,
			// new boolean[] { false, false, false, false },
			// new DialogInterface.OnMultiChoiceClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which, boolean isChecked) {
			// if (isChecked) {
			// // Toast.makeText(getApplicationContext(),
			// // "xxx" + items[which],
			// // Toast.LENGTH_LONG).show();
			// memberWhich.add(which);
			// } else
			// memberWhich.remove(which);
			// }
			// })
			// .setPositiveButton("确认",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // Toast.makeText(getApplicationContext(),
			// // "确认", Toast.LENGTH_LONG).show();
			// if (memberWhich != null
			// && memberWhich.size() > 0) {
			// for (int member : memberWhich) {
			//
			// }
			// }
			// }
			// }).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ActivityFamilyList.this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("选择要添加的管理员");
			// 指定下拉列表的显示数据;
			for (int i = 0; i < memberEntities.size(); i++) {
				items[i] = memberEntities.get(i).getUsername();
			}
			// 设置一个下拉的列表选择项
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					progressDialog.show();
					http_USERUtils.addManager(Constants.fid, memberEntities
							.get(which).getId());
				}
			});
			builder.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void resultBack(Message msg) {
		// TODO 自动生成的方法存根
		// FamilyEntity family;
		String error = null;
		switch (msg.what) {
		case Constants.queryFamiliesMyself:
			if (progressDialog.isShowing())
				progressDialog.dismiss();
			JSONObject jsonObject;
			if ("".equals((String) msg.obj) || "null".equals((String) msg.obj)
					|| (String) msg.obj == null) {
				Constants.fid = "";
				rl_add.setVisibility(View.VISIBLE);
				rl_join.setVisibility(View.VISIBLE);
				bt_quit.setVisibility(View.GONE);
				Toast.makeText(this, "没有加入任何家庭组", Toast.LENGTH_SHORT).show();
			} else {
				try {
					jsonObject = new JSONObject(msg.obj.toString());
					if (jsonObject.has("error")) {
						Toast.makeText(
								this,
								ErrorUtils.error(Integer.valueOf(jsonObject
										.getString("error"))),
								Toast.LENGTH_SHORT).show();
						Constants.fid = "";
						rl_add.setVisibility(View.VISIBLE);
						rl_join.setVisibility(View.VISIBLE);
						bt_quit.setVisibility(View.GONE);
					}
					// else {
					// rl_add.setVisibility(View.GONE);
					// rl_join.setVisibility(View.GONE);
					// }
				} catch (JSONException e1) {
					// TODO 自动生成的 catch 块
					// family = JSONParseEntityUtils.parseFamilyEntity(msg.obj
					// .toString());
					memberEntities = JSONParseEntityUtils
							.parseMemberEntities(msg.obj.toString());
					rl_add.setVisibility(View.GONE);
					rl_join.setVisibility(View.GONE);
					// Constants.fid = "";
					// rl_add.setVisibility(View.VISIBLE);
					// rl_join.setVisibility(View.VISIBLE);
					// for (MemberEntity m : memberEntities){
					// Toast.makeText(this, m.getUsername(),
					// Toast.LENGTH_SHORT).show();
					Constants.fid = memberEntities.get(0).getFid();
					if (memberEntities.size() > 0) {
						memberAdapter = new MemberAdapter(this, memberEntities);
						lv_member.setAdapter(memberAdapter);
						bt_quit.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		case Constants.delMember:
			if (progressDialog.isShowing())
				progressDialog.dismiss();

			try {
				error = ErrorUtils.error(Integer.valueOf(new JSONObject(msg.obj
						.toString()).getString("error")));
			} catch (NumberFormatException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if (error != null && !"".equals(error)) {
				Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				this.finish();
			} else if (msg.obj != null) {
				Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			break;
		case Constants.addManager:
			if (progressDialog.isShowing())
				progressDialog.dismiss();
			error = null;
			try {
				error = ErrorUtils.error(Integer.valueOf(new JSONObject(msg.obj
						.toString()).getString("error")));
			} catch (NumberFormatException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if (error != null && !"".equals(error))
				Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
			else if (msg.obj != null)
				Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
