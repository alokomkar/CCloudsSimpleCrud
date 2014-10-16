package com.example.database.handler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "ccloudsDB.db";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DBTable.DATABASE_TABLE_NAME + "("
				+ DBTable.KEY_COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DBTable.KEY_COLUMN_2 + " INTEGER,"
				+ DBTable.KEY_COLUMN_3 + " INTEGER,"
				+ DBTable.KEY_COLUMN_4 + " INTEGER"
				+ ")";
		db.execSQL(CREATE_TABLE);
		//db.close();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DBTable.DATABASE_TABLE_NAME);
		// Create tables again
		onCreate(db);

	}

	@Override
	public void onDowngrade(final SQLiteDatabase database, int oldVersion, final int newVersion ) {
		if( oldVersion < newVersion ) {
			try { 
				// Drop newer table if exists
				database.execSQL("DROP TABLE IF EXISTS " + DBTable.DATABASE_TABLE_NAME);
				//Create tables again
				onCreate(database);
			} catch( SQLiteException e ) {
				Log.d("On DownGrade", e.getMessage());
			}

		}
	}

	// Adding new company
	public int addDBTable(DBTable dbTable) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBTable.KEY_COLUMN_1, dbTable.getmColumn1());
		values.put(DBTable.KEY_COLUMN_2, dbTable.getmColumn2());
		values.put(DBTable.KEY_COLUMN_4, dbTable.getmColumn4());
		values.put(DBTable.KEY_COLUMN_3, dbTable.getmColumn3());
		// Inserting Row
		int id = (int) db.insert(DBTable.DATABASE_TABLE_NAME, null, values);
		db.close(); // Closing database connection
		return id;
	}

	//updating a existing company
	public int editDBTable(DBTable dbTable, String position) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBTable.KEY_COLUMN_1, dbTable.getmColumn1());
		values.put(DBTable.KEY_COLUMN_2, dbTable.getmColumn2());
		values.put(DBTable.KEY_COLUMN_3, dbTable.getmColumn3());
		values.put(DBTable.KEY_COLUMN_4, dbTable.getmColumn4());
		// updating row
		return db.update(DBTable.DATABASE_TABLE_NAME, values, DBTable.KEY_COLUMN_1 + " = ?",
				new String[] { String.valueOf(position) });
	}

	public DBTable getDBTable(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(DBTable.DATABASE_TABLE_NAME, new String[] { DBTable.KEY_COLUMN_1,
				DBTable.KEY_COLUMN_2, DBTable.KEY_COLUMN_4, DBTable.KEY_COLUMN_3 }, DBTable.KEY_COLUMN_1 + "=?",
				new String[] {String.valueOf(id)}, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		DBTable company = new DBTable(cursor.getInt(1),cursor.getInt(2),
				cursor.getInt(3), cursor.getInt(4));
		db.close();
		// return company
		return company;

	}



	// LIst all companys
	public String[] getAllDBTable() {
		ArrayList<String> companysList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + DBTable.DATABASE_TABLE_NAME;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String company = cursor.getString(1) + ", " + cursor.getString(2) +  ", " + cursor.getString(3);
					companysList.add(company);
				} while (cursor.moveToNext());
			}

			String[] companys = new String[companysList.size()];
			db.close();
			return (companysList.toArray(companys));
		}
		catch(Exception e) {
			Log.d("Error in getting companys from DB", e.getMessage());
			return null;
		}
	}

	// Return Cursor pointing to DBTable
	public Cursor getAllDBTableCursor() {
		String selectQuery = "SELECT  * FROM " + DBTable.DATABASE_TABLE_NAME;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if( cursor == null ) {
				return null;
			}
			else {
				cursor.moveToFirst();
				return cursor;
			}
		}
		catch(Exception e) {
			Log.d("Error in getting companys from DB", e.getMessage());
			return null;
		}
	}
	
	

}
