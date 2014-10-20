package com.example.ccloudssimplecrud;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.database.handler.DatabaseHandler;

public class CustomCursorAdapter extends CursorAdapter {

	private LayoutInflater mInflater = null;
	ViewHolderItem mViewHolderItem;
	Context mContext;
	String TAG = CustomCursorAdapter.class.getSimpleName();
	DatabaseHandler mDatabaseHandler;
	Cursor mCursor;
	ListView mListView;

	
	public CustomCursorAdapter(Context context, Cursor c, int flags, ListView listView) {
		super(context, c, flags);
		this.mContext = context;
		this.mCursor = c;
		this.mListView = listView;
	}

	@Override
	public void bindView(final View convertView, Context context, Cursor cursor) {
		
		mViewHolderItem = (ViewHolderItem) convertView.getTag();
		if( mViewHolderItem == null ) {
			Log.d(TAG, "Initializing ViewHolder");
			mViewHolderItem = new ViewHolderItem();
			mViewHolderItem.mHorizontalScrollView = (HorizontalScrollView) convertView.findViewById(R.id.horizontalScrollView1);
			mViewHolderItem.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.scrollViewLinearLyt);
			mViewHolderItem.mButton = (Button) convertView.findViewById(R.id.button1);
			mViewHolderItem.mButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					updateAction(convertView);
				}
			});
			convertView.setTag(mViewHolderItem);
			initScrollViewData(cursor); 
		}

	}
	
	
	protected void updateAction(View convertView) {
		
		LinearLayout linearLayout = null;
		if( convertView == null ) {
			
			View view = mListView.getFocusedChild();
			if( view == null ) 
				return;
			linearLayout = (LinearLayout) view.findViewById(R.id.scrollViewLinearLyt);
			if( linearLayout == null )
				return;
			
		}
		else {
			linearLayout = (LinearLayout) convertView.findViewById(R.id.scrollViewLinearLyt);
		}
		
		int childCount = mViewHolderItem.mLinearLayout.getChildCount();
		if( mDatabaseHandler == null )
			mDatabaseHandler = new DatabaseHandler(mContext);
		
		String[] data = new String[childCount]; 
		
		for( int childIndex = 0; childIndex < childCount; childIndex++ ) {
			data[childIndex] = ((EditText) linearLayout.getChildAt(childIndex)).getText().toString();
		}
		mDatabaseHandler.editEntry(data, data[0]);
		
	}

	private void initScrollViewData(Cursor cursor) {

		int columnCount = cursor.getColumnCount();
		
		if( columnCount > 0 ) {
			Log.d(TAG, "Column Count : " + columnCount);
			for( int columnIndex = 0; columnIndex < columnCount; columnIndex++ ) {
				EditText editText = getCustomEditText(columnIndex);
				editText.setText(cursor.getString(columnIndex));
				mViewHolderItem.mLinearLayout.addView(editText); 
			}
		}
	}

	private EditText getCustomEditText(int columnIndex ){
		EditText editText = new EditText( mContext );
		editText.setHorizontalScrollBarEnabled(true);
		editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		if( columnIndex == 0 ) {
			editText.setEnabled(false);
		}
		return editText;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parentViewGroup) {
		Log.d(TAG, "newView Called");
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return mInflater.inflate(R.layout.custom_list_item, null, false);
	}

	static class ViewHolderItem {
		LinearLayout mLinearLayout;
		HorizontalScrollView mHorizontalScrollView;
		Button mButton;
	}

}
