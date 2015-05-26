package com.t3ree.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	/**
	 * ������JSON�е��������
	 * 
	 * @param JSONString
	 *            JSON�ַ���
	 * @param arrayParams
	 *            JSON����������
	 * @return
	 */
	public static List<String> parseJSONArrayString(String JSONString,
			String arrayParams) {
		if (JSONString == null || JSONString.equals(""))
			return null;
		try {
			JSONObject jsonObject = new JSONObject(JSONString);
			JSONArray jsonArray = jsonObject.getJSONArray(arrayParams);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(jsonArray.getString(i));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������ͨ�������ͣ����������������
	 * 
	 * @param JSONString
	 *            JSON�ַ���
	 * @param params
	 *            JSON�� ���ļ���
	 * @param arrayParams
	 *            JSON�� ����ļ���
	 * @return
	 */
	public static Map<String, Object> parseJSONString(String JSONString,
			String[] params, String[] arrayParams) {
		if (JSONString == null || JSONString.equals(""))
			return null;
		if (params == null || params.equals(""))
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(JSONString);
			for (int i = 0; i < params.length; i++) {
				map.put(params[i], jsonObject.getString(params[i]));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (arrayParams != null) {
			for (int j = 0; j < arrayParams.length; j++) {
				List<String> list = parseJSONArrayString(JSONString,
						arrayParams[j]);
				map.put(arrayParams[j], list);
			}
		}
		return map;
	}

	/**
	 * ���ַ���������List
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<String> parseStringToList(String jsonString) {
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(jsonString);
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < jsonArray.length(); j++) {
				list.add((String) jsonArray.getString(j));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
