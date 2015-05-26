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
 * 生成该类的对象，并调用execute方法之后 首先执行的是onProExecute方法 其次执行doInBackgroup方法
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
	 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			dbManager.delAll();
		}
		return "successful";
	}

	/**
	 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
	 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	 */
	@Override
	protected void onPostExecute(String result) {

		// textView.setText("异步操作执行结束" + result);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		myProgressBar.dismiss();
		Message msg = new Message();
		msg.what = 1;
		msg.obj = myProgressBar;
		handler.sendMessage(msg);
	}

	// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
	@Override
	protected void onPreExecute() {
		myProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置进度条风格，风格为圆形，旋转的
		myProgressBar.setTitle("下载中");
		// 设置ProgressDialog 标题
		// myProgressBar.setMessage("test");
		// 设置ProgressDialog 提示信息
		// myProgressBar.setIcon(R.drawable.ic_launcher);
		// 设置ProgressDialog 标题图标
		// mypDialog.setButton("Google",this);
		// //设置ProgressDialog 的一个Button
		myProgressBar.setIndeterminate(false);
		// 设置ProgressDialog 的进度条是否不明确
		myProgressBar.setCancelable(true);
		// 设置ProgressDialog 是否可以按退回按键取消
		myProgressBar.show();
	}

	/**
	 * 这里的Intege参数对应AsyncTask中的第二个参数
	 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
	 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// int value = values[0];
		// myProgressBar.setProgress(value);
		// myProgressBar.setMessage(value + "%");
	}

}
