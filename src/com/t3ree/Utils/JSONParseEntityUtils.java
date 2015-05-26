package com.t3ree.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.t3ree.Entity.BillEntity;
import com.t3ree.Entity.FamilyEntity;
import com.t3ree.Entity.MemberEntity;

/**
 * 将得到的JSON转化成需要的entity
 * 
 * @author t3ree
 * 
 */
public class JSONParseEntityUtils {

	/**
	 * 
	 * @param JSONString
	 * @return
	 */
	public static Object[] parserBillList(String JSONString) {
		/*
		 * {"item":[{"id":"4","uid":"18","type":"1","used":"777","other":"","date"
		 * :
		 * ""},{"id":"5","uid":"18","type":"0","used":"555","other":"","date":""
		 * }
		 * ,{"id":"14","uid":"18","type":"1","used":"23","other":"","date":""},{
		 * "id":"15","uid":"18","type":"1","used":"23","other":"","date":""}]}
		 */

		try {
			String[] params = { "item" };
			Map<String, Object> map = JSONUtils.parseJSONString(JSONString,
					params, null);
			String itemString = (String) map.get(params[0]);
			List<BillEntity> billEntities = parseBillEntities(itemString);
			Object[] objects = new Object[1];
			objects[0] = billEntities;
			return objects;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static List<BillEntity> parseBillEntities(String jsonString) {
		/*
		 * {"id":"14", "uid":"18", "type":"1", "used":"23", "other":"",
		 * "date":""}
		 */
		try {
			List<String> list = JSONUtils.parseStringToList(jsonString);
			String[] params = { "id", "uid", "type", "used", "other", "date" };
			String bid, uid, type, used, other, date;
			Map<String, Object> map;
			BillEntity billEntity;
			List<BillEntity> billEntities = new ArrayList<BillEntity>();
			for (int i = 0; i < list.size(); i++) {
				map = JSONUtils.parseJSONString(list.get(i), params, null);
				bid = (String) map.get(params[0]);
				uid = (String) map.get(params[1]);
				type = (String) map.get(params[2]);
				used = (String) map.get(params[3]);
				other = (String) map.get(params[4]);
				date = (String) map.get(params[5]);
				billEntity = new BillEntity(Integer.valueOf(type),
						Float.valueOf(used), other, date);
				billEntity.setBid(bid);
				billEntity.setSynchronization(1);
				billEntities.add(billEntity);
			}
			return billEntities;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static FamilyEntity parseFamilyEntity(String jsonString) {
		/* {"id":"1","family_name":"wwe","question":"bbb","answer":"aaa"} */
		List<String> list = JSONUtils.parseStringToList(jsonString);
		String[] params = { "id", "family_name", "question" };
		String id, family_name, question;
		Map<String, Object> map;
		FamilyEntity family;
		map = JSONUtils.parseJSONString(list.get(0), params, null);
		id = (String) map.get(params[0]);
		family_name = (String) map.get(params[1]);
		question = (String) map.get(params[2]);
		family = new FamilyEntity(id, family_name, question);
		return family;
	}

	public static List<MemberEntity> parseMemberEntities(String jsonString) {
		try {
			List<String> list = JSONUtils.parseStringToList(jsonString);
			String[] params = { "fid", "id", "username" };
			String fid, id, username;
			Map<String, Object> map;
			MemberEntity memberEntity;
			List<MemberEntity> memberEntities = new ArrayList<MemberEntity>();
			for (int i = 0; i < list.size(); i++) {
				map = JSONUtils.parseJSONString(list.get(i), params, null);
				fid = (String) map.get(params[0]);
				id = (String) map.get(params[1]);
				username = (String) map.get(params[2]);
				memberEntity = new MemberEntity();
				memberEntity.setFid(fid);
				memberEntity.setId(id);
				memberEntity.setUsername(username);
				memberEntities.add(memberEntity);
			}
			return memberEntities;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
