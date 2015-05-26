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
 * 生成该类的对象，并调用execute方法之后 首先执行的是onProExecute方法 其次执行doInBackgroup方法
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
	 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
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
	 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
	 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	 */
	@Override
	protected void onPostExecute(String result) {

		// textView.setText("异步操作执行结束" + result);
		if (!"".equals(result))
			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		myProgressBar.dismiss();
		if (type == 1) {
			try {
				downLoadAsyncTask = new DownLoadAsyncTask(context, handler);
				downLoadAsyncTask.execute(1);
			} catch (MalformedURLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			dbManager.delAll();
			Constants.username = "";
			Constants.password = "";
			handler.sendEmptyMessage(0);
		}

	}

	// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置进度条风格，风格为长形，有刻度的
		myProgressBar.setTitle("上传中");
		// 设置ProgressDialog 标题
		// myProgressBar.setMessage("同步中");
		// 设置ProgressDialog 提示信息
		myProgressBar.setIcon(R.drawable.ic_launcher);
		// 设置ProgressDialog 标题图标
		myProgressBar.setProgress(59);
		// 设置ProgressDialog 进度条进度
		// this.setButton("取消", this);
		// 设置ProgressDialog 的一个Button
		myProgressBar.setIndeterminate(false);
		// 设置ProgressDialog 的进度条是否不明确
		myProgressBar.setCancelable(true);
		// 设置ProgressDialog 是否可以按退回按键取消
		myProgressBar.show();
		// textView.setText("开始执行异步线程");
	}

	/**
	 * 这里的Intege参数对应AsyncTask中的第二个参数
	 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
	 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
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
				// TODO 自动生成的 catch 块
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return "successful";

	}
}
