package com.t3ree.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "xiaominglicai.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		db.execSQL("CREATE TABLE IF NOT EXISTS record"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,bid VARCHAR,type INTERGER,used INTERGER,other VARCHAR,date VARCHAR,synchronization INTERGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根
		db.execSQL("ALTER TABLE collection ADD COLUMN other STRING");
	}

}
