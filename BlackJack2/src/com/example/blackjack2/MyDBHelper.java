package com.example.blackjack2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper{

	static String name = "myDatabase";
	static CursorFactory factory = null;
	static int version = 1;
	
	
	public MyDBHelper(Context context) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String SQL = "CREATE TABLE IF NOT EXISTS  User (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Password TEXT, Money INTEGER)";
		db.execSQL(SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String SQL = "DROP TABLE Student";
	    db.execSQL(SQL);       
	}
	
}
