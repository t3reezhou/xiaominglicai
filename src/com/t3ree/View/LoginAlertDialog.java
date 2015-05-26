package com.t3ree.View;

import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.t3ree.Utils.DownLoadAsyncTask;
import com.t3ree.Utils.UpLoadAsyncTask;

public class LoginAlertDialog {
	private Builder builder;

	public LoginAlertDialog(final Context context, final Handler handler) {
		builder = new AlertDialog.Builder(context);
		builder.setTitle("�������������ݣ��ϴ����������ݽ���ʧ");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					new UpLoadAsyncTask(context, handler).execute(1);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).setNegativeButton("����", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					new DownLoadAsyncTask(context, handler).execute(1);
				} catch (MalformedURLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		});
		builder.create();
	}

	public void show() {
		builder.show();
	}
}
