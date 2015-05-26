package com.t3ree.Utils;

import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.API.BasicApiImpl;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * ���ɸ���Ķ��󣬲�����execute����֮�� ����ִ�е���onProExecute���� ���ִ��doInBackgroup����
 * 
 */
public class DownLoadAsyncTask extends AsyncTask<Integer, Integer, String> {

	private ProgressDialog myProgressBar;
	private Context context;
	private DBManager dbManager;
	private Handler handler;

	public DownLoadAsyncTask(Context context, Handler handler)
			throws MalformedURLException {
		super();
		// this.textView = textView;
		this.context = context;
		this.handler = handler;
		this.myProgressBar = new ProgressDialog(context);
		dbManager = new DBManager(context);
	}

	/**
	 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
	 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
	 */
	@Override
	protected String doInBackground(Integer... params) {
		String result = BasicApiImpl.queryBillByOwner();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			if (jsonObject.has("error")) {
				return ErrorUtils.error(Integer.valueOf(jsonObject
						.getString("error")));
			} else {
				dbManager.delAll();
				if (result != null && !"".equals(result)) {
					Object[] object = JSONParseEntityUtils
							.parserBillList(result);
					List<BillEntity> bills = (List<BillEntity>) object[0];
					dbManager.addBills(bills);
				}
			}
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			dbManager.delAll();
		}
		return "successful";
	}

	/**
	 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
	 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	 */
	@Override
	protected void onPostExecute(String result) {

		// textView.setText("�첽����ִ�н���" + result);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		myProgressBar.dismiss();
		Message msg = new Message();
		msg.what = 1;
		msg.obj = myProgressBar;
		handler.sendMessage(msg);
	}

	// �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������
	@Override
	protected void onPreExecute() {
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		myProgressBar.setTitle("������");
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
		myProgressBar.show();
	}

	/**
	 * �����Intege������ӦAsyncTask�еĵڶ�������
	 * ��doInBackground�������У���ÿ�ε���publishProgress�������ᴥ��onProgressUpdateִ��
	 * onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// int value = values[0];
		// myProgressBar.setProgress(value);
		// myProgressBar.setMessage(value + "%");
	}

}
