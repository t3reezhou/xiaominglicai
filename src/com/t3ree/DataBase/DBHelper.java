package com.t3ree.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "xiaominglicai.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �Զ����ɵķ������
		db.execSQL("CREATE TABLE IF NOT EXISTS record"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,bid VARCHAR,type INTERGER,used INTERGER,other VARCHAR,date VARCHAR,synchronization INTERGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������
		db.execSQL("ALTER TABLE collection ADD COLUMN other STRING");
	}

}
