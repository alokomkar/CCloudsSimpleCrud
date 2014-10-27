package com.example.ccloudssimplecrud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.database.handler.DBFetchTask;
import com.example.database.handler.DBFetchTaskResultListener;
import com.example.database.handler.DatabaseHandler;

public class MainActivity extends Activity implements DBFetchTaskResultListener {

	ListView mListView;
	CustomCursorAdapter mCustomCursorAdapter;
	DatabaseHandler mDatabaseHandler;
	DBFetchTask mDbFetchTask;

	Cursor mCursor;
	int mInvokeMode = -1;
	public static final int KEY_DISPLAY = 1;
	public static final int KEY_UPDATE = 2;
	public static final int KEY_INSERT = 3;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.customListView);
		mListView.setItemsCanFocus(true);
		mListView.setLongClickable(true);
		
		mListView.setOnItemLongClickListener(mListViewItemLongClickListener);
		mDatabaseHandler = new DatabaseHandler(this);

		mInvokeMode = KEY_DISPLAY;

		executeDBFetchTask();

		Button createButton = (Button) findViewById(R.id.createBtn);
		Button updateButton = (Button) findViewById(R.id.updateBtn1);
		Button refreshButton = (Button) findViewById(R.id.refreshBtn);

		createButton.setOnClickListener(mButtonClickListener);
		updateButton.setOnClickListener(mButtonClickListener);
		refreshButton.setOnClickListener(mButtonClickListener);

	}


	OnItemLongClickListener mListViewItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> adapter, View view,
				int position, long id) {

			displayDeleteConfirmationDialog(String.valueOf(id));
			return false;
			
		}
	};

	private void executeDBFetchTask() {
		new DBFetchTask(this, mDatabaseHandler).execute();
	}

	protected void displayDeleteConfirmationDialog(final String id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete Confirmation");
		builder.setMessage("Are you sure you want to delete Entry "+id+"?");
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Do do my action here
				mDatabaseHandler.deleteDBTable(id);
				mInvokeMode = KEY_DISPLAY;
				executeDBFetchTask();
				dialog.dismiss();
			}

		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();


	}

	private void initializeListView() {

		/**
		 * TODO Need to check what flag needs to be set : 
		 * FLAG_AUTO_REQUERY - deprecated
		 * FLAG_REGISTER_CONTENT_OBSERVER - Check it
		 * http://developer.android.com/reference/android/widget/CursorAdapter.html#FLAG_AUTO_REQUERY
		 */

		mCustomCursorAdapter = new CustomCursorAdapter(this, mCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER, mListView);
		mListView.setAdapter(mCustomCursorAdapter);

	}

	OnClickListener mButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {

			switch( view.getId() ) {
			case R.id.createBtn :
				mInvokeMode = KEY_INSERT;
				createAction();
				break;
			case R.id.updateBtn1 : 
				mInvokeMode = KEY_UPDATE;
				updateAction();
				break;
			case R.id.refreshBtn : 
				mInvokeMode = KEY_DISPLAY;
				executeDBFetchTask();
				break;
			}

		}
	};

	public void createAction() {

		String rowIndex = String.valueOf(mDatabaseHandler.getMaxId()+1);

		int columnCount = mCursor.getColumnCount();
		String[] data = new String[columnCount];
		for( int index = 0; index < columnCount; index++ ) {
			data[index] = rowIndex;
		}
		mDatabaseHandler.addEntry(data );
		mInvokeMode = KEY_DISPLAY;
		executeDBFetchTask();

	}

	public void updateAction() {
		mCustomCursorAdapter.updateAction(null);
	}

	public void refreshAction() {
		initializeListView();
	}

	@Override
	public void onAsyncTaskResult(Cursor cursor) {
		mCursor = cursor;
		switch( mInvokeMode ) {
		case KEY_DISPLAY :
			initializeListView();
			break;
		case KEY_INSERT :
			createAction();
			break;
		case KEY_UPDATE :
			updateAction();
			break;
		}

	}

}
