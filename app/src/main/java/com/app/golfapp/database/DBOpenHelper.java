package com.app.golfapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
	public static Object DB_LOCK = new Object();

	public DBOpenHelper(Context context) {
		super(context, DBConstant.DATABASE_NAME, null, DBConstant.DATABASE_VERSION);
	}

	@Override
	public final void onCreate(SQLiteDatabase db) {
		// Restaurants
		db.execSQL(DBConstant.CREATE_DB_SQL_PREFIX + DBConstant.TABLE_RESTAURANTS
				+ " (id integer primary key autoincrement, "
				+ DBConstant.RESTAURANTS_NAME + " TEXT, "
				+ DBConstant.RESTAURANTS_LIST_ID + " INTEGER, "
				+ DBConstant.RESTAURANTS_IS_ADDED_BY_ME + " INTEGER, "
				+ DBConstant.RESTAURANTS_YELP_ID + " TEXT, "
				+ DBConstant.RESTAURANTS_IMAGE_URL + " TEXT, "
				+ DBConstant.RESTAURANTS_PRICE + " TEXT, "
				+ DBConstant.RESTAURANTS_RAITING + " REAL, "
				+ DBConstant.RESTAURANTS_REVIEW_COUNT + " INTEGER, "
				+ DBConstant.RESTAURANTS_ADDRESS1 + " TEXT, "
				+ DBConstant.RESTAURANTS_CITY + " TEXT, "
				+ DBConstant.RESTAURANTS_STATE + " TEXT, "
				+ DBConstant.RESTAURANTS_ZIPCODE + " TEXT, "
				+ DBConstant.RESTAURANTS_COUNTRY + " TEXT, "
				+ DBConstant.RESTAURANTS_LATITUDE + " REAL, "
				+ DBConstant.RESTAURANTS_LONGITUDE + " REAL)");
	}

	public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("---[DEBUG]----", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		
		db.execSQL(DBConstant.DELETE_DB_SQL_PREFIX + DBConstant.TABLE_RESTAURANTS);
		
		onCreate(db);
	}
}