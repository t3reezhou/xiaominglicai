package com.t3ree.Utils;

import android.content.Context;
import android.widget.Toast;

public class ErrorUtils {
	public static String error(int error) {
		switch (error) {
		case 0:
			return "����post";
		case 1:
			return "�û���������Ϊ��";
		case 3:
			return "��֤����";
		case 4:
			return "��Ȩ��";
		case 5:
			return "�ش����";
		case 6:
			return "����ʧ��";
		case 7:
			return "ɾ��ʧ��";
		case 8:
			return "�û��Ѵ���";
		case 9:
			return "�����쳣";
		case 10:
			return "�ü�ͥ���Ѵ���";
		default:
			return null;
		}
	}
}
