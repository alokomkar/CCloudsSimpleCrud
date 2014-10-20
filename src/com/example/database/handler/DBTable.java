package com.example.database.handler;

/**
 * @author alokomkar
 *
 */

public class DBTable {

	int mColumn1;
	int mColumn2;
	int mColumn3;
	int mColumn4;
	
	public static String DATABASE_TABLE_NAME = "dbtable";
	
	public static String KEY_COLUMN_1 = "_id";
	public static String KEY_COLUMN_2 = "column_2";
	public static String KEY_COLUMN_3 = "column_3";
	public static String KEY_COLUMN_4 = "column_4";
	public static String KEY_COLUMN_5 = "column_5";
	
	public static final String[] TableColumns_Version_1 = {KEY_COLUMN_1, KEY_COLUMN_2,  KEY_COLUMN_3, KEY_COLUMN_4};
	public static final String[] TableColumns_Version_2 = {KEY_COLUMN_1, KEY_COLUMN_2,  KEY_COLUMN_3, KEY_COLUMN_4, KEY_COLUMN_5};
	
	
	
	/**
	 * @param mCompanyId
	 * @param mFullName
	 * @param mShortName
	 * @param mPhoneNumber
	 */
	public DBTable(int mCompanyId, int mFullName, int mShortName,
			int mPhoneNumber) {
		super();
		this.mColumn1 = mCompanyId;
		this.mColumn2 = mFullName;
		this.mColumn3 = mShortName;
		this.mColumn4 = mPhoneNumber;
	}

	/**
	 * Default Constructor
	 */
	public DBTable() {
		super();
	}

	/**
	 * @return the mCompanyId
	 */
	public int getmColumn1() {
		return mColumn1;
	}

	/**
	 * @param mCompanyId the mCompanyId to set
	 */
	public void setmColumn1(int mCompanyId) {
		this.mColumn1 = mCompanyId;
	}

	/**
	 * @return the mFullName
	 */
	public int getmColumn2() {
		return mColumn2;
	}

	/**
	 * @param mFullName the mFullName to set
	 */
	public void setmColumn2(int mFullName) {
		this.mColumn2 = mFullName;
	}

	/**
	 * @return the mShortName
	 */
	public int getmColumn3() {
		return mColumn3;
	}

	/**
	 * @param mShortName the mShortName to set
	 */
	public void setmColumn3(int mShortName) {
		this.mColumn3 = mShortName;
	}

	/**
	 * @return the mPhoneNumber
	 */
	public int getmColumn4() {
		return mColumn4;
	}

	/**
	 * @param mPhoneNumber the mPhoneNumber to set
	 */
	public void setmColumn4(int mPhoneNumber) {
		this.mColumn4 = mPhoneNumber;
	}
	
	
	
}
