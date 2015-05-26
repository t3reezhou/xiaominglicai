package com.t3ree.Utils;

import com.t3ree.Activity.Constants;
import com.t3ree.Listener.DataListener;

import android.os.Handler;
import android.os.Message;

public class HTTP_USERUtils {
	private static final String tag = "HTTP_USERUtils";
	DataListener dataListener;
	Handler handler;

	public HTTP_USERUtils(DataListener dataListener) {
		this.dataListener = dataListener;
		createHandler();
	}

	private void createHandler() {
		// TODO 自动生成的方法存根
		handler = new Handler() {
			public void handleMessage(Message msg) {
				Message resultMessage = new Message();
				resultMessage.what = msg.what;
				switch (msg.what) {
				case Constants.Login:
					// 登陆
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.register:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.delBill:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.queryFamiliesMyself:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.addFamily:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.queryFamilies:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.addMemberByQuestion:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.queryBillByManager:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.delMember:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				case Constants.addManager:
					resultMessage.obj = msg.obj;
					dataListener.resultBack(resultMessage);
					break;
				}
			}
		};
	}

	public void login(String userName, String password) {
		Object[] objects = new Object[2];
		objects[0] = userName;
		objects[1] = password;
		new ThreadUtils(handler, Constants.Login, objects);
	}

	public void register(String userName, String password) {
		Object[] objects = new Object[2];
		objects[0] = userName;
		objects[1] = password;
		new ThreadUtils(handler, Constants.register, objects);
	}

	public void addBill(String type, String used, String other, String date) {
		Object[] objects = new Object[4];
		objects[0] = type;
		objects[1] = used;
		objects[2] = other;
		objects[3] = date;
		new ThreadUtils(handler, Constants.addBill, objects);
	}

	public void addFamily(String family_name, String question, String answer) {
		Object[] objects = new Object[3];
		objects[0] = family_name;
		objects[1] = question;
		objects[2] = answer;
		new ThreadUtils(handler, Constants.addFamily, objects);
	}

	public void addMemberByManager(String uid, String fid, String manager) {
		Object[] objects = new Object[3];
		objects[0] = uid;
		objects[1] = fid;
		objects[2] = manager;
		new ThreadUtils(handler, Constants.addMemberByManager, objects);
	}

	public void addMemberByQuestion(String fid, String answer) {
		Object[] objects = new Object[2];
		objects[0] = fid;
		objects[1] = answer;
		new ThreadUtils(handler, Constants.addMemberByQuestion, objects);
	}

	public void delBill(String bid) {
		Object[] objects = new Object[1];
		objects[0] = bid;
		new ThreadUtils(handler, Constants.delBill, objects);
	}

	public void delMember(String fid, String uid) {
		Object[] objects = new Object[2];
		objects[0] = fid;
		objects[1] = uid;
		new ThreadUtils(handler, Constants.delMember, objects);
	}

	public void queryBillByManager(String fid, String uid) {
		Object[] objects = new Object[2];
		objects[0] = fid;
		objects[1] = uid;
		new ThreadUtils(handler, Constants.queryBillByManager, objects);
	}

	public void queryBillByOwner() {
		new ThreadUtils(handler, Constants.queryBillByManager, null);
	}

	public void queryFamilies(String fname) {
		Object[] objects = new Object[1];
		objects[0] = fname;
		new ThreadUtils(handler, Constants.queryFamilies, objects);
	}

	public void queryFamiliesMyself() {
		new ThreadUtils(handler, Constants.queryFamiliesMyself, null);
	}

	public void queryMember(String fid) {
		Object[] objects = new Object[1];
		objects[0] = fid;
		new ThreadUtils(handler, Constants.queryMember, objects);
	}

	public void updateBill(String bid, String type, String used, String other,
			String date) {
		Object[] objects = new Object[5];
		objects[0] = bid;
		objects[1] = type;
		objects[2] = used;
		objects[3] = other;
		objects[4] = date;
		new ThreadUtils(handler, Constants.updateBill, objects);
	}

	public void updateFamily(String fid, String family_name, String question,
			String answer) {
		Object[] objects = new Object[4];
		objects[0] = fid;
		objects[1] = family_name;
		objects[2] = question;
		objects[3] = answer;
		new ThreadUtils(handler, Constants.updateFamily, objects);
	}

	public void addManager(String fid, String uid) {
		Object[] objects = new Object[2];
		objects[0] = fid;
		objects[1] = uid;
		new ThreadUtils(handler, Constants.addManager, objects);
	}
}
