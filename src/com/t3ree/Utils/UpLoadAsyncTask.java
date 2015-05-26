package com.t3ree.Utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.t3ree.API.BasicApiImpl;
import com.t3ree.Activity.Constants;
import com.t3ree.DataBase.DBManager;
import com.t3ree.Entity.BillEntity;
import com.xiaominglicai.R;

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
public class UpLoadAsyncTask extends AsyncTask<Integer, Integer, String> {

	private ProgressDialog myProgressBar;
	private Context context;
	private DBManager dbManager;
	private int uploadLength;
	private int resultLength;
	private DownLoadAsyncTask downLoadAsyncTask;
	private Handler handler;

	private int type;

	public UpLoadAsyncTask(Context context, Handler handler)
			throws MalformedURLException {
		super();
		// this.textView = textView;
		this.myProgressBar = new ProgressDialog(context);
		this.context = context;
		this.handler = handler;
		dbManager = new DBManager(context);
	}

	/**
	 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
	 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
	 */
	@Override
	protected String doInBackground(Integer... params) {
		type = params[0];
		List<BillEntity> bills = dbManager.queryBySynchronization();
		if (bills.size() > 0)
			return upload(bills);
		else
			return "";
	}

	/**
	 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
	 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	 */
	@Override
	protected void onPostExecute(String result) {

		// textView.setText("�첽����ִ�н���" + result);
		if (!"".equals(result))
			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		myProgressBar.dismiss();
		if (type == 1) {
			try {
				downLoadAsyncTask = new DownLoadAsyncTask(context, handler);
				downLoadAsyncTask.execute(1);
			} catch (MalformedURLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		} else {
			dbManager.delAll();
			Constants.username = "";
			Constants.password = "";
			handler.sendEmptyMessage(0);
		}

	}

	// �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// ���ý�������񣬷��Ϊ���Σ��п̶ȵ�
		myProgressBar.setTitle("�ϴ���");
		// ����ProgressDialog ����
		// myProgressBar.setMessage("ͬ����");
		// ����ProgressDialog ��ʾ��Ϣ
		myProgressBar.setIcon(R.drawable.ic_launcher);
		// ����ProgressDialog ����ͼ��
		myProgressBar.setProgress(59);
		// ����ProgressDialog ����������
		// this.setButton("ȡ��", this);
		// ����ProgressDialog ��һ��Button
		myProgressBar.setIndeterminate(false);
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		myProgressBar.setCancelable(true);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		myProgressBar.show();
		// textView.setText("��ʼִ���첽�߳�");
	}

	/**
	 * �����Intege������ӦAsyncTask�еĵڶ�������
	 * ��doInBackground�������У���ÿ�ε���publishProgress�������ᴥ��onProgressUpdateִ��
	 * onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		int value = values[0];
		myProgressBar.setProgress(value);
		myProgressBar.setMessage(value + "%");
	}

	private String upload(List<BillEntity> bills) {
		List<BillEntity> addBills = new ArrayList<BillEntity>();
		List<BillEntity> updateBills = new ArrayList<BillEntity>();
		JSONObject jsonObject;
		for (BillEntity bill : bills) {
			if (bill.getBid() == null && bill.getSynchronization() == 0) {
				addBills.add(bill);
				System.out.println(bill.getPosition());
			} else if (bill.getBid() != null && bill.getSynchronization() == 0) {
				updateBills.add(bill);
				System.out.println(bill.getPosition());
			}
		}
		uploadLength = addBills.size() + updateBills.size();
		for (BillEntity bill : addBills) {
			try {
				jsonObject = new JSONObject(BasicApiImpl.addBill(
						String.valueOf(bill.getType()),
						String.valueOf(bill.getUsed()), bill.getOther(),
						bill.getDate()).trim());
				if (jsonObject.has("error")) {
					return ErrorUtils.error(Integer.valueOf(jsonObject
							.getString("error")));
				} else if (jsonObject.has("msg")) {
					resultLength++;
					bill.setSynchronization(1);
					dbManager.updateBill(bill);
					publishProgress((int) (resultLength * 100) / uploadLength);
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		for (BillEntity bill : updateBills) {
			try {
				jsonObject = new JSONObject(BasicApiImpl.updateBill(
						bill.getBid(), String.valueOf(bill.getType()),
						String.valueOf(bill.getUsed()), bill.getOther(),
						bill.getDate()));
				if (jsonObject.has("error")) {
					return ErrorUtils.error(Integer.valueOf(jsonObject
							.getString("error")));
				} else if (jsonObject.has("msg")) {
					resultLength++;
					bill.setSynchronization(1);
					dbManager.updateBill(bill);
					publishProgress((int) (resultLength * 100) / uploadLength);
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return "successful";

	}
}
