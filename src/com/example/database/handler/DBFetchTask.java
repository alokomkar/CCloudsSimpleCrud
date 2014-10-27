package com.example.database.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.ccloudssimplecrud.MainActivity;

public class DBFetchTask extends AsyncTask<Void, Void, Void>{

	Context mContext;
	DatabaseHandler mDatabaseHandler;
	Cursor mCursor;
	ProgressDialog mProgressDialog;
	DBFetchTaskResultListener mDbFetchTaskResultListener;
	
	public DBFetchTask( MainActivity activity, DatabaseHandler databaseHandler ) {
		this.mContext = activity;
		this.mDbFetchTaskResultListener = (DBFetchTaskResultListener) activity;
		this.mProgressDialog = new ProgressDialog(mContext);
		this.mDatabaseHandler = databaseHandler;
	}
	
	@Override
	protected void onPreExecute() {
		mProgressDialog.setMessage("Refreshing Data, Please Wait...");
		mProgressDialog.show();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if( mProgressDialog.isShowing() ) {
			mProgressDialog.dismiss();
		}
		mDbFetchTaskResultListener.onAsyncTaskResult(mCursor);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(mContext);
		}
		mCursor = mDatabaseHandler.getAllDBTableCursor();
		return null;
	}

}
