package com.t3ree.View;

import com.t3ree.DataBase.DBManager;
import com.t3ree.Listener.DataListener;
import com.xiaominglicai.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class Dialog_Del extends Dialog implements OnClickListener {
	public static final int DELETE_BILL_LOCAL = 0X01215;
	public static final int DELETE_BILL_ONLINE = 0X01216;
	private Context context;
	private RelativeLayout rl_del_local;
	private RelativeLayout rl_del_online;
	private RelativeLayout rl_cancle;
	private DataListener datalistener;

	// private int data;

	public Dialog_Del(Context context, DataListener datalistener) {
		super(context, R.style.dialog_with_alpha);
		setContentView(R.layout.dialog_del);
		this.context = context;
		this.datalistener = datalistener;
		// this.data = data;
		WindowManager manager = getWindow().getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		p.width = (int) display.getWidth();
		Window window = getWindow();
		window.setAttributes(p);
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.buttomStyle);
		setCancelable(true);
		findView();
		// TODO 自动生成的构造函数存根
	}

	private void findView() {
		// TODO 自动生成的方法存根
		rl_del_local = (RelativeLayout) findViewById(R.id.rl_dialog_del_del);
		rl_del_online = (RelativeLayout) findViewById(R.id.rl_dialog_del_online);
		rl_cancle = (RelativeLayout) findViewById(R.id.rl_dialog_del_cancle);
		rl_del_local.setOnClickListener(this);
		rl_del_online.setOnClickListener(this);
		rl_cancle.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Message msg;
		switch (v.getId()) {
		case R.id.rl_dialog_del_del:
			// DBManager mgr = new DBManager(context);
			// mgr.delBill(data);
			msg = new Message();
			msg.what = DELETE_BILL_LOCAL;
			datalistener.resultBack(msg);
			this.dismiss();
			break;
		case R.id.rl_dialog_del_online:
			msg = new Message();
			msg.what = DELETE_BILL_ONLINE;
			datalistener.resultBack(msg);
			this.dismiss();
			break;
		case R.id.rl_dialog_del_cancle:
			this.dismiss();
			break;
		default:
			break;
		}
	}
}
