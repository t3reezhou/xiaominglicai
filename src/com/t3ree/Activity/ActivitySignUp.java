package com.t3ree.Activity;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.DataBase.DBManager;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.DownLoadAsyncTask;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.MD5andK1;
import com.t3ree.View.LoginAlertDialog;
import com.xiaominglicai.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySignUp extends Activity implements OnClickListener,
		DataListener {
	private EditText et_signup_user;
	private EditText et_signup_password;
	private EditText et_signup_again;
	private Button btn_signup_signup;
	private Button btn_top_back;
	private Button btn_top_login;
	private SharedPreferences sp;
	private Editor editor;

	private HTTP_USERUtils http_USERUtils;
	private DBManager dbManager;
	private Handler handler;
	private ProgressDialog myProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		http_USERUtils = new HTTP_USERUtils(this);
		dbManager = new DBManager(this);
		myProgressBar = new ProgressDialog(this);
		initProgressDialog();
		findView();
		fillView();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				finish();
			}
		};
	}

	private void initProgressDialog() {
		// TODO 自动生成的方法存根
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置进度条风格，风格为圆形，旋转的
		myProgressBar.setTitle("注册");
		myProgressBar.setIndeterminate(false);
		myProgressBar.setCancelable(true);
	}

	private void findView() {
		// TODO 自动生成的方法存根
		et_signup_user = (EditText) findViewById(R.id.et_signup_user);
		et_signup_password = (EditText) findViewById(R.id.et_signup_password);
		et_signup_again = (EditText) findViewById(R.id.et_signup_again);
		btn_signup_signup = (Button) findViewById(R.id.btn_signup_signup);
		btn_top_back = (Button) findViewById(R.id.top_back);
		btn_top_login = (Button) findViewById(R.id.top_btn);

		btn_signup_signup.setOnClickListener(this);
		btn_top_back.setOnClickListener(this);
		btn_top_login.setOnClickListener(this);
	}

	private void fillView() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_signup_signup:
			myProgressBar.show();
			String user = et_signup_user.getText().toString().trim();
			String password = et_signup_password.getText().toString().trim();
			String again = et_signup_again.getText().toString().trim();
			if ("".equals(password) || "".equals(user)) {
				Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
				if (myProgressBar.isShowing())
					myProgressBar.dismiss();
			} else {
				if (again.equals(password))
					http_USERUtils.register(user, MD5andK1.MD5(password));
				else {
					Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
					if (myProgressBar.isShowing())
						myProgressBar.dismiss();
				}
			}
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			Intent intent = new Intent();
			intent.setClass(this, ActivityLogin.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void resultBack(Message msg) {
		// TODO 自动生成的方法存根
		switch (msg.what) {
		case Constants.register:
			if (myProgressBar.isShowing())
				myProgressBar.dismiss();
			JSONObject json;
			try {
				json = new JSONObject(msg.obj.toString().trim());
				if (json.has("error")) {
					int error = Integer.valueOf(json.getString("error"));
					// switch (error) {
					// case 2:
					// Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
					// break;
					// case 6:
					// Toast.makeText(this, "该用户已存在", Toast.LENGTH_SHORT)
					// .show();
					// break;
					// default:
					// break;
					// }
					Toast.makeText(this, ErrorUtils.error(error),
							Toast.LENGTH_SHORT).show();
				} else if (json.has("id")) {
					sp = getSharedPreferences("core_user", MODE_PRIVATE);
					editor = sp.edit();
					editor.putString("username", et_signup_user.getText()
							.toString().trim());
					editor.putString("password", MD5andK1
							.MD5(et_signup_password.getText().toString()));
					editor.commit();
					Constants.username = sp.getString("username", "");
					Constants.password = sp.getString("password", "");

					if (dbManager.quetyAll().size() > 0)
						new LoginAlertDialog(ActivitySignUp.this, handler)
								.show();
					else {
						Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
