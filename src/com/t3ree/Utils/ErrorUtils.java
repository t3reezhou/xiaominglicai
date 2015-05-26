package com.t3ree.Utils;

import android.content.Context;
import android.widget.Toast;

public class ErrorUtils {
	public static String error(int error) {
		switch (error) {
		case 0:
			return "不是post";
		case 1:
			return "用户名或密码为空";
		case 3:
			return "验证错误";
		case 4:
			return "无权限";
		case 5:
			return "回答错误";
		case 6:
			return "更新失败";
		case 7:
			return "删除失败";
		case 8:
			return "用户已存在";
		case 9:
			return "网络异常";
		case 10:
			return "该家庭组已存在";
		default:
			return null;
		}
	}
}
