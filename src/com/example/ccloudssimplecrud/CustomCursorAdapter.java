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

public class CustomCursorAdapter extends CursorAdapter {

	private LayoutInflater mInflater = null;
	ViewHolderItem mViewHolderItem;
	Context mContext;
	String TAG = CustomCursorAdapter.class.getSimpleName();
	
	public CustomCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		
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
					
					updateAction();
					
				}
			});
			convertView.setTag(mViewHolderItem);
		}

		initScrollViewData(cursor); 

	}
	
	protected void updateAction() {
		
		int childCount = mViewHolderItem.mLinearLayout.getChildCount();
		
		for( int childIndex = 0; childIndex < childCount; childIndex++ ) {
			
		}
		
	}

	private void initScrollViewData(Cursor cursor) {

		int columnCount = cursor.getColumnCount();

		if( columnCount > 0 ) {
			Log.d(TAG, "Column Count : " + columnCount);
			for( int columnIndex = 0; columnIndex < columnCount; columnIndex++ ) {
				EditText editText = getCustomEditText();
				editText.setText(cursor.getString(columnIndex));
				mViewHolderItem.mLinearLayout.addView(editText); 
			}
		}
	}

	private EditText getCustomEditText( ){
		EditText editText = new EditText( mContext );
		editText.setHorizontalScrollBarEnabled(true);
		editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		return editText;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parentViewGroup) {
		Log.d(TAG, "newView Called");
		return mInflater.inflate(R.layout.custom_list_item, parentViewGroup, false);
	}

	static class ViewHolderItem {
		LinearLayout mLinearLayout;
		HorizontalScrollView mHorizontalScrollView;
		Button mButton;
	}

}
