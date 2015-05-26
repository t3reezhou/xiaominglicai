package com.t3ree.Utils;

import com.t3ree.API.BasicApiImpl;
import com.t3ree.Activity.Constants;

import android.os.Handler;
import android.os.Message;

public class ThreadUtils extends Thread {
	private String Tag = "ThreadUtils";
	private Handler callBackHandler;
	private int TYPE;
	private Object object;

	public ThreadUtils(Handler handler, int type, Object object) {
		callBackHandler = handler;
		TYPE = type;
		this.object = object;
		this.start();
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		// super.run();
		Object resultObject = null;
		Message msg;
		try {
			switch (TYPE) {
			case Constants.Login:
				resultObject = this.login();
				break;
			case Constants.register:
				resultObject = this.register();
				break;
			case Constants.addBill:
				resultObject = this.addBill();
				break;
			case Constants.addFamily:
				resultObject = this.addFamily();
				break;
			case Constants.addMemberByManager:
				resultObject = this.addMemberByManager();
				break;
			case Constants.addMemberByQuestion:
				resultObject = this.addMemberByQuestion();
				break;
			case Constants.delBill:
				resultObject = this.delBill();
				break;
			case Constants.delMember:
				resultObject = this.delMember();
				break;
			case Constants.queryBillByManager:
				resultObject = this.queryBillByManager();
				break;
			case Constants.queryBillByOwner:
				resultObject = this.queryBillByOwner();
				break;
			case Constants.queryFamilies:
				resultObject = this.queryFamilies();
				break;
			case Constants.queryFamiliesMyself:
				resultObject = this.queryFamiliesMyself();
				break;
			case Constants.queryMember:
				resultObject = this.queryMember();
				break;
			case Constants.updateBill:
				resultObject = this.updateBill();
				break;
			case Constants.updateFamily:
				resultObject = this.updateFamily();
				break;
			case Constants.addManager:
				resultObject = this.addManager();
				break;
			}
			msg = new Message();
			msg.what = TYPE;
			msg.obj = resultObject;
			callBackHandler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message();
			msg.what = Constants.error;
			callBackHandler.sendMessage(msg);
		}
	}

	public String login() {
		Object[] objects = (Object[]) object;
		String name = (String) objects[0];
		String pwd = (String) objects[1];
		String result = BasicApiImpl.login(name, pwd);
		return result;

	}

	public String register() {
		Object[] objects = (Object[]) object;
		String username = (String) objects[0];
		String password = (String) objects[1];
		String result = BasicApiImpl.register(username, password);
		return result;
	}

	public String addBill() {
		Object[] objects = (Object[]) object;
		String type = (String) objects[0];
		String used = (String) objects[1];
		String other = (String) objects[2];
		String date = (String) objects[3];
		String result = BasicApiImpl.addBill(type, used, other, date);
		return result;
	}

	public String addFamily() {
		Object[] objects = (Object[]) object;
		String family_name = (String) objects[0];
		String question = (String) objects[1];
		String answer = (String) objects[2];
		String result = BasicApiImpl.addFamily(family_name, question, answer);
		return result;
	}

	public String addMemberByManager() {
		Object[] objects = (Object[]) object;
		String uid = (String) objects[0];
		String fid = (String) objects[1];
		String manager = (String) objects[2];
		String result = BasicApiImpl.addMemberByManager(uid, fid, manager);
		return result;
	}

	public String addMemberByQuestion() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String answer = (String) objects[1];
		String result = BasicApiImpl.addMemberByQuestion(fid, answer);
		return result;
	}

	public String delBill() {
		Object[] objects = (Object[]) object;
		String bid = (String) objects[0];
		String result = BasicApiImpl.delBill(bid);
		return result;
	}

	public String delMember() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String uid = (String) objects[1];
		String result = BasicApiImpl.delMember(fid, uid);
		return result;
	}

	public String queryBillByManager() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String uid = (String) objects[1];
		String result = BasicApiImpl.queryBillByManager(fid, uid);
		return result;
	}

	public String queryBillByOwner() {
		String result = BasicApiImpl.queryBillByOwner();
		return result;
	}

	public String queryFamilies() {
		Object[] objects = (Object[]) object;
		String fname = (String) objects[0];
		String result = BasicApiImpl.queryFamilies(fname);
		return result;
	}

	public String queryFamiliesMyself() {
		String result = BasicApiImpl.queryFamiliesMyself();
		return result;
	}

	public String queryMember() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String result = BasicApiImpl.queryMember(fid);
		return result;
	}

	public String updateBill() {
		Object[] objects = (Object[]) object;
		String bid = (String) objects[0];
		String type = (String) objects[1];
		String used = (String) objects[2];
		String other = (String) objects[3];
		String date = (String) objects[4];
		String result = BasicApiImpl.updateBill(bid, type, used, other, date);
		return result;
	}

	public String updateFamily() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String family_name = (String) objects[1];
		String question = (String) objects[2];
		String answer = (String) objects[3];
		String result = BasicApiImpl.updateFamily(fid, family_name, question,
				answer);
		return result;
	}

	public String addManager() {
		Object[] objects = (Object[]) object;
		String fid = (String) objects[0];
		String uid = (String) objects[1];
		String result = BasicApiImpl.addManager(fid, uid);
		return result;
	}

}
