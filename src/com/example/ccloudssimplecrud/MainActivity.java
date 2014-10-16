package com.example.ccloudssimplecrud;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.database.handler.DBTable;
import com.example.database.handler.DatabaseHandler;

public class MainActivity extends Activity {

	ListView mListView;
	CustomCursorAdapter mCustomCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (ListView) findViewById(R.id.customListView);
		DatabaseHandler databaseHandler = new DatabaseHandler(this);
		/*databaseHandler.addDBTable(new DBTable(1, 1, 1, 1));
		databaseHandler.addDBTable(new DBTable(2, 1, 1, 1));
		databaseHandler.addDBTable(new DBTable(3, 1, 1, 1));
		databaseHandler.addDBTable(new DBTable(4, 1, 1, 1));
		databaseHandler.addDBTable(new DBTable(5, 1, 1, 1));*/
		Cursor cursor = databaseHandler.getAllDBTableCursor();
		
		/**
		 * TODO Need to check what flag needs to be set : 
		 * FLAG_AUTO_REQUERY - deprecated
		 * FLAG_REGISTER_CONTENT_OBSERVER - Check it
		 * http://developer.android.com/reference/android/widget/CursorAdapter.html#FLAG_AUTO_REQUERY
		 */
		
		mCustomCursorAdapter = new CustomCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mListView.setAdapter(mCustomCursorAdapter);
		
		Button createButton = (Button) findViewById(R.id.createBtn);
		Button updateButton = (Button) findViewById(R.id.updateBtn1);
		Button refreshButton = (Button) findViewById(R.id.refreshBtn);
		
		createButton.setOnClickListener(mButtonClickListener);
		updateButton.setOnClickListener(mButtonClickListener);
		refreshButton.setOnClickListener(mButtonClickListener);
		
	}
	
	OnClickListener mButtonClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			
			switch( view.getId() ) {
			case R.id.createBtn :
				break;
			case R.id.updateBtn1 : 
				break;
			case R.id.refreshBtn : 
				refreshAction();
				break;
			}
			
		}
	};
	
	public void refreshAction() {
		mCustomCursorAdapter.notifyDataSetChanged();
	}

	

}
