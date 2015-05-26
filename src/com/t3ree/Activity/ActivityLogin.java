package com.t3ree.Activity;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.API.BasicApiImpl;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.DownLoadAsyncTask;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.MD5andK1;
import com.t3ree.Utils.UpLoadAsyncTask;
import com.t3ree.View.LoginAlertDialog;
import com.xiaominglicai.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class ActivityLogin extends Activity implements OnClickListener,
		DataListener {
	private EditText et_login_user;
	private EditText et_login_password;
	private Button btn_login_login;
	private Button btn_top_back;
	private Button btn_top_sign;
	private SharedPreferences sp;
	private Editor editor;
	private ProgressDialog myProgressBar;

	private HTTP_USERUtils http_USERUtils;
	private Handler handler;
	private DBManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		http_USERUtils = new HTTP_USERUtils(this);
		myProgressBar = new ProgressDialog(this);
		dbManager = new DBManager(this);
		initProgressBar();
		findView();
		fillView();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO �Զ����ɵķ������
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					ProgressDialog pd = (ProgressDialog) msg.obj;
					if (pd.isShowing())
						pd.dismiss();
					finish();
					break;

				default:
					break;
				}
			}
		};
	}

	private void initProgressBar() {
		// TODO �Զ����ɵķ������
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		myProgressBar.setTitle("��¼");
		// ����ProgressDialog ����
		// myProgressBar.setMessage("test");
		// ����ProgressDialog ��ʾ��Ϣ
		// myProgressBar.setIcon(R.drawable.ic_launcher);
		// ����ProgressDialog ����ͼ��
		// mypDialog.setButton("Google",this);
		// //����ProgressDialog ��һ��Button
		myProgressBar.setIndeterminate(false);
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		myProgressBar.setCancelable(true);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
	}

	private void findView() {
		// TODO �Զ����ɵķ������
		et_login_user = (EditText) findViewById(R.id.et_login_user);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_top_back = (Button) findViewById(R.id.top_back);
		btn_top_sign = (Button) findViewById(R.id.top_btn);

		btn_login_login.setOnClickListener(this);
		btn_top_back.setOnClickListener(this);
		btn_top_sign.setOnClickListener(this);
	}

	private void fillView() {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.btn_login_login:
			myProgressBar.show();
			String user = et_login_user.getText().toString().trim();
			String password = MD5andK1.MD5(et_login_password.getText()
					.toString().trim());
			http_USERUtils.login(user, password);
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.top_btn:
			Intent intent = new Intent();
			intent.setClass(this, ActivitySignUp.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void resultBack(Message msg) {
		// TODO �Զ����ɵķ������
		switch (msg.what) {
		case Constants.Login:
			if (myProgressBar.isShowing())
				myProgressBar.dismiss();
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(msg.obj.toString());
				if (jsonObject.has("error")) {
					Toast.makeText(
							this,
							ErrorUtils.error(Integer.valueOf(jsonObject
									.getString("error"))), Toast.LENGTH_SHORT)
							.show();
				} else {

				}
			} catch (JSONException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
				if (msg.obj != null && !"".equals(msg.obj.toString())) {
					String uid = msg.obj.toString();
					Constants.uid = uid.substring(1, uid.length() - 1);
				}
				sp = getSharedPreferences("core_user", MODE_PRIVATE);
				editor = sp.edit();
				editor.putString("username", et_login_user.getText().toString()
						.trim());
				editor.putString("password",
						MD5andK1.MD5(et_login_password.getText().toString()));
				editor.putString("uid", Constants.uid);
				editor.commit();

				Constants.username = sp.getString("username", "");
				Constants.password = sp.getString("password", "");

				if (dbManager.quetyAll().size() > 0)
					new LoginAlertDialog(ActivityLogin.this, handler).show();
				else {
					try {
						new DownLoadAsyncTask(ActivityLogin.this, handler)
								.execute(1);
					} catch (MalformedURLException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
			break;
		default:
			break;
		}
	}
}
