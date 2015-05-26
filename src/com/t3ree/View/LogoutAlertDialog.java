package com.t3ree.View;

import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.t3ree.DataBase.DBManager;
import com.t3ree.Utils.DownLoadAsyncTask;
import com.t3ree.Utils.UpLoadAsyncTask;

public class LogoutAlertDialog {
	private Builder builder;

	public Builder getBuilder() {
		return builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	public LogoutAlertDialog(final Context context, final Handler handler,
			final DBManager dbManager) {
		builder = new AlertDialog.Builder(context);
		builder.setTitle("本地有未同步数据，上传？否则数据将丢失");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					new UpLoadAsyncTask(context, handler).execute(0);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).setNegativeButton("无视", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dbManager.delAll();
				handler.sendEmptyMessage(0);
			}
		});
		builder.create();
	}

	public void show() {
		builder.show();
	}
}
