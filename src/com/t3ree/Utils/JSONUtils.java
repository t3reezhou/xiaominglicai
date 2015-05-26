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
	 * 适用于JSON中的数组解析
	 * 
	 * @param JSONString
	 *            JSON字符串
	 * @param arrayParams
	 *            JSON中数组名称
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
	 * 适用普通数据类型，多个数组数据类型
	 * 
	 * @param JSONString
	 *            JSON字符串
	 * @param params
	 *            JSON中 键的集合
	 * @param arrayParams
	 *            JSON中 数组的集合
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
	 * 将字符串解析成List
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
