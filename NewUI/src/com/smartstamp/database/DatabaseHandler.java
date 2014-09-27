/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.smartstamp.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_LOGIN = "login";
	// Stmap table name
	private static final String TABLE_STAMP = "stamp";
	// Company_all table name
	private static final String TABLE_COMPANY_ALL = "company_all";
	// Franchise_all table name
	private static final String TABLE_FRANCHISE_ALL = "franchise_all";
	// Franchise_used table name
	private static final String TABLE_FRANCHISE_USED = "franchise_used";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_AGE = "age";

	// Stamp Table Columns names
	private static final String KEY_SEMAIL = "email";
	private static final String KEY_COMPANY_CODE = "company_code";
	private static final String KEY_FRANCHISE_CODE = "franchise_code";
	private static final String KEY_COUPON_STAMP = "coupon_stamp";
	private static final String KEY_FLAG = "flag";
	private static final String KEY_TIME = "time";

	// Franchise Table Columns name
	// 같은 이름이라 주석처리, 하지만 사용 한다는 걸 표기하기 위하여 써둠.
	// private static final String KEY_COMPANY_CODE = "company_code";
	// private static final String KEY_FRANCHISE_CODE = "franchise_code";
	private static final String KEY_FRANCHISE_NAME = "franchise_name";
	private static final String KEY_FRANCHISE_LOCATION1 = "franchise_location1";
	private static final String KEY_FRANCHISE_LOCATION2 = "franchise_location2";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_PHONE_NUM = "phone_num";

	// Company Table Columns name
	// 같은 이름이라 주석처리, 하지만 사용 한다는 걸 표기하기 위하여 써둠.
	//private static final String KEY_COMPANY_CODE = "company_code";
	private static final String KEY_COMPANY_NAME = "company_name";

	private static final String CREATE_LOGIN_TABLE = "CREATE TABLE "
			+ TABLE_LOGIN + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL
			+ " TEXT UNIQUE," + KEY_UID + " TEXT," + KEY_GENDER + " TEXT,"
			+ KEY_AGE + " TEXT," + KEY_CREATED_AT + " TEXT" + ")";

	private static final String CREATE_STMAP_TABLE = "CREATE TABLE "
			+ TABLE_STAMP + "(" + KEY_SEMAIL + " TEXT," + KEY_COMPANY_CODE
			+ " TEXT," + KEY_FRANCHISE_CODE + " TEXT," + KEY_COUPON_STAMP
			+ " TEXT," + KEY_FLAG + " TEXT," + KEY_TIME + " TEXT" + ")";

	private static final String CREATE_FRANCHISEE_USED_TABLE = "CREATE TABLE "
			+ TABLE_FRANCHISE_USED + "(" + KEY_COMPANY_CODE + " TEXT,"
			+ KEY_FRANCHISE_CODE + " TEXT," + KEY_FRANCHISE_NAME + " TEXT,"
			+ KEY_FRANCHISE_LOCATION1 + " TEXT," + KEY_FRANCHISE_LOCATION2
			+ " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PHONE_NUM + " TEXT" + ")";

	private static final String CREATE_COMPANY_ALL_TABLE = "CREATE TABLE "
			+ TABLE_COMPANY_ALL + "(" + KEY_COMPANY_CODE + " TEXT,"
			+ KEY_COMPANY_NAME + " TEXT" + ")";

	private static final String CREATE_FRANCHISEE_ALL_TABLE = "CREATE TABLE "
			+ TABLE_FRANCHISE_ALL + "(" + KEY_COMPANY_CODE + " TEXT,"
			+ KEY_FRANCHISE_CODE + " TEXT," + KEY_FRANCHISE_NAME + " TEXT,"
			+ KEY_FRANCHISE_LOCATION1 + " TEXT," + KEY_FRANCHISE_LOCATION2
			+ " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PHONE_NUM + " TEXT" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_LOGIN_TABLE);
		db.execSQL(CREATE_STMAP_TABLE);
		db.execSQL(CREATE_FRANCHISEE_USED_TABLE);
		db.execSQL(CREATE_COMPANY_ALL_TABLE);
		db.execSQL(CREATE_FRANCHISEE_ALL_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAMP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRANCHISE_USED);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_ALL);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRANCHISE_ALL);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */

	public void putUser(String email, String uid, String gender, String age,
			String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_GENDER, gender);
		values.put(KEY_AGE, age);
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);

	}

	public void putlog(String email, String company_code,
			String franchise_code, String coupon_stamp, String flag, String time) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_SEMAIL, email);// email
		values.put(KEY_COMPANY_CODE, company_code); // company
		values.put(KEY_FRANCHISE_CODE, franchise_code); // franchise
		values.put(KEY_COUPON_STAMP, coupon_stamp); // coupon
		values.put(KEY_FLAG, flag); // flag
		values.put(KEY_TIME, time); // time

		// Inserting Row

		db.insert(TABLE_STAMP, null, values);
	}

	public void putfranchise_used(String company_code, String franchise_code,
			String franchise_name, String franchise_location1,
			String franchise_location2, String address, String phone_num) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_COMPANY_CODE, company_code);// company_code
		values.put(KEY_FRANCHISE_CODE, franchise_code); // franchise_code
		values.put(KEY_FRANCHISE_NAME, franchise_name); // franchise_name
		values.put(KEY_FRANCHISE_LOCATION1, franchise_location1); // franchise_location1
		values.put(KEY_FRANCHISE_LOCATION2, franchise_location2); // franchise_location2
		values.put(KEY_ADDRESS, address); // address
		values.put(KEY_PHONE_NUM, phone_num); // phone_num

		// Inserting Row

		db.insert(TABLE_FRANCHISE_USED, null, values);
	}

	public void putcompany_all(String company_code, String company_name) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_COMPANY_CODE, company_code);// company_code
		values.put(KEY_COMPANY_NAME, company_name); // franchise_code

		db.insert(TABLE_COMPANY_ALL, null, values);
	}

	public void putfranchise_all(String company_code, String franchise_code,
			String franchise_name, String franchise_location1,
			String franchise_location2, String address, String phone_num) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_COMPANY_CODE, company_code);// company_code
		values.put(KEY_FRANCHISE_CODE, franchise_code); // franchise_code
		values.put(KEY_FRANCHISE_NAME, franchise_name); // franchise_name
		values.put(KEY_FRANCHISE_LOCATION1, franchise_location1); // franchise_location1
		values.put(KEY_FRANCHISE_LOCATION2, franchise_location2); // franchise_location2
		values.put(KEY_ADDRESS, address); // address
		values.put(KEY_PHONE_NUM, phone_num); // phone_num

		db.insert(TABLE_FRANCHISE_ALL, null, values);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();

		String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("email", cursor.getString(1));
			user.put("uid", cursor.getString(2));
			user.put("gender", cursor.getString(3));
			user.put("age", cursor.getString(4));
			user.put("created_at", cursor.getString(5));
		}
		// cursor.close();

		// db.close();
		// return user
		return user;
	}

	public ArrayList<HashMap<String, String>> getStampDetails() {
		ArrayList<HashMap<String, String>> coupon = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM " + TABLE_STAMP;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getcoupon = new HashMap<String, String>();
				getcoupon.put("email", cursor.getString(0));
				getcoupon.put("company_code", cursor.getString(1));
				getcoupon.put("franchise_code", cursor.getString(2));
				getcoupon.put("coupon_stamp", cursor.getString(3));
				getcoupon.put("flag", cursor.getString(4));
				getcoupon.put("time", cursor.getString(5));
				coupon.add(getcoupon);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return coupon;
	}

	public ArrayList<HashMap<String, String>> getStampList() {
		ArrayList<HashMap<String, String>> coupon = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT company_code,franchise_code,time, count(company_code),SUM(coupon_stamp) FROM stamp group by company_code,franchise_code order by time";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getcoupon = new HashMap<String, String>();
				getcoupon.put("company_code", cursor.getString(0));
				getcoupon.put("franchise_code", cursor.getString(1));
				getcoupon.put("time", cursor.getString(2));
				getcoupon.put("count(company_code)", cursor.getString(3));
				getcoupon.put("SUM(coupon_stamp)", cursor.getString(4));
				coupon.add(getcoupon);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return coupon;
	}

	public ArrayList<HashMap<String, String>> getFranchise_used() {
		ArrayList<HashMap<String, String>> franchise = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM " + TABLE_FRANCHISE_USED;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getfranchise = new HashMap<String, String>();
				getfranchise.put("company_code", cursor.getString(0));
				getfranchise.put("franchise_code", cursor.getString(1));
				getfranchise.put("franchise_name", cursor.getString(2));
				getfranchise.put("franchise_location1", cursor.getString(3));
				getfranchise.put("franchise_location2", cursor.getString(4));
				getfranchise.put("address", cursor.getString(5));
				getfranchise.put("phone_num", cursor.getString(6));
				franchise.add(getfranchise);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return franchise;
	}

	public ArrayList<HashMap<String, String>> getCompany_all() {
		ArrayList<HashMap<String, String>> company = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM " + TABLE_COMPANY_ALL;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getcompany = new HashMap<String, String>();
				getcompany.put("company_code", cursor.getString(0));
				getcompany.put("company_name", cursor.getString(1));
				company.add(getcompany);

			} while (cursor.moveToNext());

		}
		return company;
	}

	public ArrayList<HashMap<String, String>> getFranchise_all() {
		ArrayList<HashMap<String, String>> franchise = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM " + TABLE_FRANCHISE_ALL;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getfranchise = new HashMap<String, String>();
				getfranchise.put("company_code", cursor.getString(0));
				getfranchise.put("franchise_code", cursor.getString(1));
				getfranchise.put("franchise_name", cursor.getString(2));
				getfranchise.put("franchise_location1", cursor.getString(3));
				getfranchise.put("franchise_location2", cursor.getString(4));
				getfranchise.put("address", cursor.getString(5));
				getfranchise.put("phone_num", cursor.getString(6));
				franchise.add(getfranchise);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return franchise;
	}

	
	public ArrayList<HashMap<String, String>> getTopCompany() {
		ArrayList<HashMap<String, String>> topcompany = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT company_code,franchise_code,time, count(company_code) FROM stamp group by company_code ORDER BY count(company_code) DESC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> gettopcompany = new HashMap<String, String>();
				gettopcompany.put("company_code", cursor.getString(0));
				gettopcompany.put("franchise_code", cursor.getString(1));
				gettopcompany.put("time", cursor.getString(2));
				gettopcompany.put("count(company_code)", cursor.getString(3));
				topcompany.add(gettopcompany);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return topcompany;
	}
	
	
	public ArrayList<HashMap<String, String>> getTopFranchise() {
		ArrayList<HashMap<String, String>> topfranchise = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT company_code,franchise_code,time, count(company_code) FROM stamp group by company_code,franchise_code ORDER BY count(company_code), time DESC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> gettopfranchise = new HashMap<String, String>();
				gettopfranchise.put("company_code", cursor.getString(0));
				gettopfranchise.put("franchise_code", cursor.getString(1));
				gettopfranchise.put("time", cursor.getString(2));
				gettopfranchise.put("count(company_code)", cursor.getString(3));
				topfranchise.add(gettopfranchise);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return topfranchise;
	}
	
	
	public ArrayList<HashMap<String, String>> getStamp_used() {
		ArrayList<HashMap<String, String>> coupon = new ArrayList<HashMap<String, String>>();

		String selectQuery = "SELECT * FROM stamp WHERE coupon_stamp = -10 order by time DESC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			do {
				HashMap<String, String> getcoupon = new HashMap<String, String>();
				getcoupon.put("email", cursor.getString(0));
				getcoupon.put("company_code", cursor.getString(1));
				getcoupon.put("franchise_code", cursor.getString(2));
				getcoupon.put("coupon_stamp", cursor.getString(3));
				getcoupon.put("flag", cursor.getString(4));
				getcoupon.put("time", cursor.getString(5));
				coupon.add(getcoupon);

			} while (cursor.moveToNext());

		}
		// cursor.close();

		// db.close();
		// return user
		return coupon;
	}
	
	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		// db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_STAMP, null, null);
		db.delete(TABLE_FRANCHISE_USED, null, null);
		db.delete(TABLE_COMPANY_ALL, null, null);
		db.delete(TABLE_FRANCHISE_ALL, null, null);

		// db.close();
	}

	public void resetStamp() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_STAMP, null, null);
		// db.close();
	}

	public void resetFranchise_used() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_FRANCHISE_USED, null, null);
		// db.close();
	}

	public void resetCompany_all() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_COMPANY_ALL, null, null);
		// db.close();
	}

	public void resetFranchise_all() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_FRANCHISE_ALL, null, null);
		// db.close();
	}

	public synchronized void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}
