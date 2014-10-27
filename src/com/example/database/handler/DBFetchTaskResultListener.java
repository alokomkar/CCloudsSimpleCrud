package com.example.database.handler;

import android.database.Cursor;

public interface DBFetchTaskResultListener {
	public void onAsyncTaskResult( Cursor cursor );
}
