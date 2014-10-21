package com.example.database.handler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private String TAG = getClass().getSimpleName();
	// Database Version
	public static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "ccloudsDB.db";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = CreateTableByVersion();
		db.execSQL(CREATE_TABLE);
		//db.close();

	}
	
	public String CreateTableByVersion() {
		String create_table = null;
		switch( DATABASE_VERSION ) {
		case 1 :
			create_table = "CREATE TABLE IF NOT EXISTS " + DBTable.DATABASE_TABLE_NAME + "("
					+ DBTable.KEY_COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ DBTable.KEY_COLUMN_2 + " INTEGER,"
					+ DBTable.KEY_COLUMN_3 + " INTEGER,"
					+ DBTable.KEY_COLUMN_4 + " INTEGER"
					+ ")";
			break;
		case 2 :
			create_table = "CREATE TABLE IF NOT EXISTS " + DBTable.DATABASE_TABLE_NAME + "("
					+ DBTable.KEY_COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ DBTable.KEY_COLUMN_2 + " INTEGER,"
					+ DBTable.KEY_COLUMN_3 + " INTEGER,"
					+ DBTable.KEY_COLUMN_4 + " INTEGER,"
					+ DBTable.KEY_COLUMN_5 + " INTEGER"
					+ ")";
			break;
		
		}
		
		return create_table;
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		Log.d(TAG, "Upgrade from " + oldVersion + " to " + newVersion );
		db.execSQL("DROP TABLE IF EXISTS " + DBTable.DATABASE_TABLE_NAME);
		// Create tables again
		onCreate(db);

	}

	@Override
	public void onDowngrade(final SQLiteDatabase database, int oldVersion, final int newVersion ) {
		if( newVersion < oldVersion ) {
			try { 
				Log.d(TAG, "Downgrade from " + newVersion + " to " + oldVersion );
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

	//Addition of row without pojo
	public int addEntry( String[] data ) {

		int id = 0;
		SQLiteDatabase database = this.getWritableDatabase();
		String noOfElements = getNumberOfElements(data.length);
		
		SQLiteStatement statement = database.compileStatement("INSERT INTO "+ DBTable.DATABASE_TABLE_NAME +" VALUES ("+noOfElements+")");
		//ContentValues values = new ContentValues();
		database.beginTransaction();
		statement.clearBindings();
		for( int index = 0; index < data.length; index++ ) {
			statement.bindString(index+1, data[index]);
			//values.put(DBTable.TableColumns[index], data[index]);
		}
		statement.execute();
		database.setTransactionSuccessful();
		database.endTransaction();
		database.close();
		//id = (int) database.insert(DBTable.DATABASE_TABLE_NAME, null, values);
		return id;

	}

	private String getNumberOfElements(int length) {
		String element = "";
		for( int index = 0; index < length; index++ ) {
			element += ( index == length -1 ) ? "?" : "?,";
		}
		return element;
	}

	//updating a existing company
	public int editDBTable( DBTable dbTable, String position ) {
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

	public int editEntry( String[] data, String position ) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		@SuppressWarnings("unused")
		String[] columnNames = DATABASE_VERSION == 1 ? DBTable.TableColumns_Version_1 : DBTable.TableColumns_Version_2;
		for( int index = 0; index < data.length; index++ ) {
			values.put(columnNames[index], data[index]);
		}
		int result = database.update(DBTable.DATABASE_TABLE_NAME, values, DBTable.KEY_COLUMN_1 + " = ?",
				new String[] { String.valueOf( position ) });
		database.close();
		return result;
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
	
	public int deleteDBTable( String id ) {
		int deleteResult = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		deleteResult = db.delete(DBTable.DATABASE_TABLE_NAME, DBTable.KEY_COLUMN_1 + "=?", new String[]{id});
		return deleteResult;
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
	
	public int getMaxId( ) {
		String selectQuery = "Select MAX("+DBTable.KEY_COLUMN_1+") from "+DBTable.DATABASE_TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if( cursor == null ) 
			return 0;
		if( cursor.moveToFirst() ) {
			int id = cursor.getInt(0);
			return id;
		}
		else 
			return 0;
	}



}
