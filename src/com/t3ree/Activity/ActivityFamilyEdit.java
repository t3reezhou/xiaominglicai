package com.t3ree.Activity;

import org.json.JSONObject;

import com.t3ree.Entity.FamilyEntity;
import com.t3ree.Listener.DataListener;
import com.t3ree.Utils.ErrorUtils;
import com.t3ree.Utils.HTTP_USERUtils;
import com.t3ree.Utils.JSONParseEntityUtils;
import com.xiaominglicai.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityFamilyEdit extends Activity implements OnClickListener,
		DataListener {
	private HTTP_USERUtils http_USERUtils;
	private EditText et_familyName;
	private EditText et_question;
	private EditText et_answer;
	private Button btn_post;
	private Button top_back;
	private LinearLayout ll_familyName;
	private LinearLayout ll_question;
	private LinearLayout ll_answer;
	private TextView tv_question;

	private int type;
	private FamilyEntity family;
	private ProgressDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_family);
		http_USERUtils = new HTTP_USERUtils(this);
		dialog = new ProgressDialog(this);
		iniDialog();
		findView();
		fillView();
	}

	private void iniDialog() {
		// TODO 自动生成的方法存根
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(false);
	}

	private void findView() {
		// TODO 自动生成的方法存根
		et_familyName = (EditText) findViewById(R.id.et_editfamily_fname);
		et_question = (EditText) findViewById(R.id.et_editfamily_question);
		et_answer = (EditText) findViewById(R.id.et_editfamily_answer);
		btn_post = (Button) findViewById(R.id.btn_editfamily_post);
		top_back = (Button) findViewById(R.id.top_back);
		ll_familyName = (LinearLayout) findViewById(R.id.ll_editfamily_fname);
		ll_question = (LinearLayout) findViewById(R.id.ll_editfamily_question);
		ll_answer = (LinearLayout) findViewById(R.id.ll_editfamily_answer);
		tv_question = (TextView) findViewById(R.id.tv_editfamily_question);

		btn_post.setOnClickListener(this);
		top_back.setOnClickListener(this);
	}

	private void fillView() {
		// TODO 自动生成的方法存根
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		switch (type) {
		case 1:
			break;
		case 2:
			ll_question.setVisibility(View.GONE);
			ll_answer.setVisibility(View.GONE);
			btn_post.setText("加入");
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_editfamily_post:
			switch (type) {
			case 1:
				if (!"".equals(et_familyName.getText().toString())
						&& et_familyName.getText() != null
						&& !"".equals(et_question.getText().toString())
						&& et_question != null
						&& !"".equals(et_answer.getText().toString())
						&& et_answer != null) {
					dialog.show();
					http_USERUtils.addFamily(
							et_familyName.getText().toString(), et_question
									.getText().toString(), et_answer.getText()
									.toString());
				} else
					Toast.makeText(this, "输入不标准,请填满", Toast.LENGTH_SHORT)
							.show();
				break;
			case 2:
				if (!"".equals(et_familyName.getText().toString())
						&& et_familyName.getText() != null) {
					dialog.show();
					http_USERUtils.queryFamilies(et_familyName.getText()
							.toString());
				} else
					Toast.makeText(this, "未输入任何值", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				if (!"".equals(et_answer.getText().toString())
						&& et_answer.getText() != null) {
					dialog.show();
					http_USERUtils.addMemberByQuestion(family.getId(),
							et_answer.getText().toString());
				} else
					Toast.makeText(this, "未输入任何值", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			break;
		case R.id.top_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void resultBack(Message msg) {
		// TODO 自动生成的方法存根
		if (dialog.isShowing())
			dialog.dismiss();
		JSONObject jsonObject;
		switch (msg.what) {
		case Constants.queryFamilies:
			if (msg.obj != null && !"null".equals(msg.obj.toString())
					&& !"".equals(msg.obj.toString())) {
				family = JSONParseEntityUtils.parseFamilyEntity(msg.obj
						.toString());
				tv_question.setText("问题: " + family.getQuestion());
				tv_question.setVisibility(View.VISIBLE);
				ll_familyName.setVisibility(View.GONE);
				ll_answer.setVisibility(View.VISIBLE);
				type = 3;
			} else
				Toast.makeText(this, "该家庭组不存在", Toast.LENGTH_SHORT).show();
			// Toast.makeText(this, (String) msg.obj,
			// Toast.LENGTH_SHORT).show();
			break;
		case Constants.addMemberByQuestion:
			try {
				jsonObject = new JSONObject(msg.obj.toString());
				if (jsonObject.has("error")) {
					Toast.makeText(
							this,
							ErrorUtils.error(Integer.valueOf(jsonObject
									.getString("error"))), Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(this, "successful", Toast.LENGTH_SHORT)
							.show();
					finish();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// Toast.makeText(this, (String) msg.obj,
			// Toast.LENGTH_SHORT).show();
			break;
		case Constants.addFamily:
			try {
				jsonObject = new JSONObject(msg.obj.toString());
				if (jsonObject.has("error")) {
					Toast.makeText(
							this,
							ErrorUtils.error(Integer.valueOf(jsonObject
									.getString("error"))), Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(this, "successful", Toast.LENGTH_SHORT)
							.show();
					finish();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// Toast.makeText(this, (String) msg.obj,
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
