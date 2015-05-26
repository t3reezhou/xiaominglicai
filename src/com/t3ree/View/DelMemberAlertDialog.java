package com.t3ree.View;

import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.Activity.Constants;
import com.t3ree.Adapter.MemberAdapter;
import com.t3ree.Entity.MemberEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class DelMemberAlertDialog implements DataListener {
	private Builder builder;
	private HTTP_USERUtils http_USERUtils;
	private Handler handler;
	private ProgressDialog dialog;
	private Context context;

	public DelMemberAlertDialog(final Context context, final String uid,
			Handler handler) {
		http_USERUtils = new HTTP_USERUtils(this);
		dialog = new ProgressDialog(context);
		this.context = context;
		this.handler = handler;
		initDialog();
		builder = new AlertDialog.Builder(context);
		builder.setTitle("踢走该用户");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DelMemberAlertDialog.this.dialog.show();
				http_USERUtils.delMember(Constants.fid, uid);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
	}

	public void show() {
		builder.show();
	}

	private void initDialog() {
		// TODO 自动生成的方法存根
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(false);
		dialog.setCancelable(false);
	}

	@Override
	public void resultBack(Message msg) {
		// TODO 自动生成的方法存根
		switch (msg.what) {
		case Constants.delMember:
			if (dialog.isShowing())
				dialog.dismiss();
			String error = null;
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
				Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
			} else if (msg.obj != null) {
				Toast.makeText(context, "successful", Toast.LENGTH_SHORT)
						.show();
				msg.what = MemberAdapter.DELESUCCESSFUL;
				handler.sendMessage(msg);
			}
			break;
		}
	}
}
