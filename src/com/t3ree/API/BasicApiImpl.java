package com.t3ree.API;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.t3ree.Activity.Constants;
import com.t3ree.Utils.MD5andK1;
import com.t3ree.Utils.ThreadUtils;

public class BasicApiImpl {
	private BasicApiImpl() {

	}

	public static String login(String username, String password) {
		// 设置参数，仿html表单提交
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", username));
		paramList.add(new BasicNameValuePair("password", password));
		String url = Constants.userService + Constants.loginServer;
		return post(url, paramList);
	}

	public static String register(String username, String password) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", username));
		paramList.add(new BasicNameValuePair("password", password));
		String url = Constants.userService + Constants.registerServer;
		return post(url, paramList);
	}

	public static String addBill(String type, String used, String other,
			String date) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("type", type));
		paramList.add(new BasicNameValuePair("used", used));
		paramList.add(new BasicNameValuePair("other", other));
		paramList.add(new BasicNameValuePair("date", date));
		String url = Constants.userService + Constants.addBillServer;
		return post(url, paramList);
	}

	public static String addFamily(String family_name, String question,
			String answer) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("family_name", family_name));
		paramList.add(new BasicNameValuePair("question", question));
		paramList.add(new BasicNameValuePair("answer", answer));
		String url = Constants.userService + Constants.addFamilyServer;
		return post(url, paramList);
	}

	public static String addMemberByManager(String uid, String fid,
			String manager) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("uid", uid));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("manager", manager));
		String url = Constants.userService + Constants.addMemberByManagerServer;
		return post(url, paramList);
	}

	public static String addMemberByQuestion(String fid, String answer) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("answer", answer));
		String url = Constants.userService
				+ Constants.addMemberByQuestionServer;
		return post(url, paramList);
	}

	public static String delBill(String bid) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("bid", bid));
		String url = Constants.userService + Constants.delBillServer;
		return post(url, paramList);
	}

	public static String delMember(String fid, String uid) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("uid", uid));
		String url = Constants.userService + Constants.delMemberServer;
		return post(url, paramList);
	}

	public static String queryBillByManager(String fid, String uid) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("uid", uid));
		String url = Constants.userService + Constants.queryBillByManagerServer;
		return post(url, paramList);
	}

	public static String queryBillByOwner() {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		String url = Constants.userService + Constants.queryBillByOwnerServer;
		return post(url, paramList);
	}

	public static String queryFamilies(String fname) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("family_name", fname));
		String url = Constants.userService + Constants.queryFamiliesServer;
		return post(url, paramList);
	}

	public static String queryFamiliesMyself() {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		String url = Constants.userService
				+ Constants.queryFamiliesMyselfServer;
		return post(url, paramList);
	}

	public static String queryMember(String fid) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		String url = Constants.userService + Constants.queryMembersServer;
		return post(url, paramList);
	}

	public static String updateBill(String bid, String type, String used,
			String other, String date) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("bid", bid));
		paramList.add(new BasicNameValuePair("type", type));
		paramList.add(new BasicNameValuePair("used", used));
		paramList.add(new BasicNameValuePair("other", other));
		paramList.add(new BasicNameValuePair("date", date));
		String url = Constants.userService + Constants.updateBillServer;
		return post(url, paramList);
	}

	public static String updateFamily(String fid, String family_name,
			String question, String answer) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("family_name", family_name));
		paramList.add(new BasicNameValuePair("question", question));
		paramList.add(new BasicNameValuePair("answer", answer));
		String url = Constants.userService + Constants.updateFamilyServer;
		return post(url, paramList);
	}

	public static String addManager(String fid, String uid) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("username", Constants.username));
		paramList.add(new BasicNameValuePair("password", Constants.password));
		paramList.add(new BasicNameValuePair("fid", fid));
		paramList.add(new BasicNameValuePair("uid", uid));
		String url = Constants.userService + Constants.addManagerServer;
		return post(url, paramList);
	}

	public static String get(String url) {
		String result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// 仿地址链接直接跟参数，如：http://127.0.0.1:8080/test/test.php?name=;
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONStringer jsonText = new JSONStringer();
			try {
				jsonText.object();
				jsonText.key("error");
				jsonText.value("9");
				jsonText.endObject();
			} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			return jsonText.toString();
		}
		return result;
	}

	public static String post(String url, List<NameValuePair> paramList) {
		String result = null;
		try {
			// 创建连接
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
			// 发送HttpPost请求，并返回HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(post);
			// 判断请求响应状态码，状态码为200表示服务端成功响应了客户端的请求
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回结果
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			JSONStringer jsonText = new JSONStringer();
			try {
				jsonText.object();
				jsonText.key("error");
				jsonText.value("9");
				jsonText.endObject();
			} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			return jsonText.toString();
		}
		return result;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			Log.i("NetWorkState", "Unavailabel");
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.i("NetWorkState", "Availabel");
						return true;
					}
				}
			}
		}
		return false;
	}

}
