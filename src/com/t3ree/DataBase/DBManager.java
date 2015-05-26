package com.t3ree.DataBase;

import java.util.ArrayList;
import java.util.List;

import com.t3ree.Entity.BillEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;

	public DBManager(Context context) {
		this.context = context;
		this.helper = new DBHelper(context);
		this.db = helper.getWritableDatabase();
	}

	public void addBill(BillEntity billEntity) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO record VALUES(null,null,?,?,?,?,?)",
					new Object[] { billEntity.getType(), billEntity.getUsed(),
							billEntity.getOther(), billEntity.getDate(),
							billEntity.getSynchronization() });
			db.setTransactionSuccessful();
			Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
		} finally {
			db.endTransaction();
		}
	}

	public void addBills(List<BillEntity> bills) {
		db.beginTransaction();
		try {
			for (BillEntity bill : bills) {
				db.execSQL(
						"INSERT INTO record VALUES(null,?,?,?,?,?,?)",
						new Object[] { bill.getBid(), bill.getType(),
								bill.getUsed(), bill.getOther(),
								bill.getDate(), bill.getSynchronization() });
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void updateBill(BillEntity bill) {
		ContentValues cv = new ContentValues();
		cv.put("type", bill.getType());
		cv.put("used", bill.getUsed());
		cv.put("other", bill.getOther());
		cv.put("date", bill.getDate());
		cv.put("synchronization", bill.getSynchronization());
		db.update("record", cv, "id = ?",
				new String[] { String.valueOf(bill.getId()) });
	}

	public void delBill(int id) {
		db.delete("record", "id=?", new String[] { String.valueOf(id) });
		Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
	}

	public List<BillEntity> quetyAll() {
		ArrayList<BillEntity> bills = new ArrayList<BillEntity>();
		BillEntity billEntity;
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			billEntity = new BillEntity();
			billEntity.setId(c.getInt(c.getColumnIndex("id")));
			billEntity.setBid(c.getString(c.getColumnIndex("bid")));
			billEntity.setType(c.getInt(c.getColumnIndex("type")));
			billEntity.setUsed(c.getFloat(c.getColumnIndex("used")));
			billEntity.setOther(c.getString(c.getColumnIndex("other")));
			billEntity.setDate(c.getString(c.getColumnIndex("date")));
			billEntity.setSynchronization(c.getInt(c
					.getColumnIndex("synchronization")));
			bills.add(billEntity);
		}
		c.close();
		return bills;
	}

	private Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM record", null);
		return c;
	}

	public List<BillEntity> queryBySynchronization() {
		ArrayList<BillEntity> bills = new ArrayList<BillEntity>();
		BillEntity billEntity;
		Cursor c = db.rawQuery(
				"SELECT * FROM record WHERE synchronization='0'", null);
		while (c.moveToNext()) {
			billEntity = new BillEntity();
			billEntity.setId(c.getInt(c.getColumnIndex("id")));
			// billEntity.setUid(c.getInt(c.getColumnIndex("uid")));
			billEntity.setType(c.getInt(c.getColumnIndex("type")));
			billEntity.setUsed(c.getFloat(c.getColumnIndex("used")));
			billEntity.setOther(c.getString(c.getColumnIndex("other")));
			billEntity.setDate(c.getString(c.getColumnIndex("date")));
			billEntity.setBid(c.getString(c.getColumnIndex("bid")));
			bills.add(billEntity);
		}
		c.close();
		return bills;
	}

	public List<BillEntity> queryByMonth(String time, int start, int end) {
		ArrayList<BillEntity> bills = new ArrayList<BillEntity>();
		BillEntity billEntity;
		Cursor c = db.rawQuery("SELECT * FROM record WHERE type>=" + start
				+ " and type<=" + end + " and date LIKE '" + time + "%'", null);
		while (c.moveToNext()) {
			billEntity = new BillEntity();
			billEntity.setId(c.getInt(c.getColumnIndex("id")));
			// billEntity.setUid(c.getInt(c.getColumnIndex("uid")));
			billEntity.setType(c.getInt(c.getColumnIndex("type")));
			billEntity.setUsed(c.getFloat(c.getColumnIndex("used")));
			billEntity.setOther(c.getString(c.getColumnIndex("other")));
			billEntity.setDate(c.getString(c.getColumnIndex("date")));
			billEntity.setBid(c.getString(c.getColumnIndex("bid")));
			bills.add(billEntity);
		}
		c.close();
		return bills;
	}

	public void delAll() {
		String sql = "delete from record;" + " select * from sqlite_sequence;"
				+ " update sqlite_sequence set seq=0 where name='record';";
		db.execSQL(sql);
	}

	public void closeDB() {
		db.close();
	}
}
