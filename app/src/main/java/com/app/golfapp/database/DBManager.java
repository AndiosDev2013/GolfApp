package com.app.golfapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBManager instance;

	private DBOpenHelper mdbhelper;

	public DBManager(Context context) {
		this.mdbhelper = new DBOpenHelper(context);
		instance = this;
	}

	public static DBManager getInstance() {
		return instance; 
	}

	/*
	 * Restaurants
	 */
	synchronized public int addOrUpdateList(int id, String name) {
		synchronized (DBOpenHelper.DB_LOCK) {
			int value = 0;

			ContentValues values = new ContentValues();
			values.put(DBConstant.RESTAURANTS_NAME, name);
			values.put(DBConstant.RESTAURANTS_LIST_ID, -1);
			values.put(DBConstant.RESTAURANTS_IS_ADDED_BY_ME, 1);

			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
			if (mdb != null) {
				if (id >= 0) {
					value = mdb.update(DBConstant.TABLE_RESTAURANTS, values, DBConstant.TBL_COLUMN_ID+"='"+id+"'", null);
				} else {
					value = (int)mdb.insert(DBConstant.TABLE_RESTAURANTS, null, values);
				}
				mdb.close();
			}
			return value;
		}
	}

//	synchronized public ArrayList<Restaurant> getAllList() {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			ArrayList<Restaurant> list = new ArrayList<>();
//			SQLiteDatabase mdb = mdbhelper.getReadableDatabase();
//			if (mdb != null) {
//				String sql = String.format(Locale.getDefault(), "SELECT * FROM %s WHERE %s<0 ORDER BY %s DESC",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.RESTAURANTS_LIST_ID, DBConstant.TBL_COLUMN_ID);
//
//				Cursor cursor = mdb.rawQuery(sql, null);
//				while (cursor.moveToNext()) {
//					Restaurant item = new Restaurant();
//					item.id = cursor.getInt(cursor.getColumnIndex(DBConstant.TBL_COLUMN_ID));
//					item.name = cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_NAME));
//					item.isAddedByMe = true;
//					item.mInfo = null;
//
//					list.add(item);
//				}
//				cursor.close();
//				mdb.close();
//			}
//			return list;
//		}
//	}
//
//	synchronized public int addOrUpdateRestaurant(int id, Restaurant rest) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//		    int value = 0;
//
//			ContentValues values = new ContentValues();
//			values.put(DBConstant.RESTAURANTS_NAME, rest.name);
//			values.put(DBConstant.RESTAURANTS_LIST_ID, rest.listId);
//
//			if (rest.hasValidBusiness()) {
//				values.put(DBConstant.RESTAURANTS_IS_ADDED_BY_ME, 0);
//				values.put(DBConstant.RESTAURANTS_YELP_ID, rest.mInfo.getId());
//				values.put(DBConstant.RESTAURANTS_IMAGE_URL, rest.mInfo.getImageUrl());
//				values.put(DBConstant.RESTAURANTS_PRICE, rest.mInfo.getPrice());
//				values.put(DBConstant.RESTAURANTS_RAITING, rest.mInfo.getRating());
//				values.put(DBConstant.RESTAURANTS_REVIEW_COUNT, rest.mInfo.getReviewCount());
//				values.put(DBConstant.RESTAURANTS_ADDRESS1, rest.mInfo.getLocation().getAddress1());
//				values.put(DBConstant.RESTAURANTS_CITY, rest.mInfo.getLocation().getCity());
//				values.put(DBConstant.RESTAURANTS_STATE, rest.mInfo.getLocation().getState());
//				values.put(DBConstant.RESTAURANTS_ZIPCODE, rest.mInfo.getLocation().getZipCode());
//				values.put(DBConstant.RESTAURANTS_COUNTRY, rest.mInfo.getLocation().getCountry());
//				values.put(DBConstant.RESTAURANTS_LATITUDE, rest.mInfo.getCoordinates().getLatitude());
//				values.put(DBConstant.RESTAURANTS_LONGITUDE, rest.mInfo.getCoordinates().getLongitude());
//			}
//			else {
//				values.put(DBConstant.RESTAURANTS_IS_ADDED_BY_ME, 1);
//			}
//
//			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
//			if (mdb != null) {
//				if (id >= 0) {
//					value = mdb.update(DBConstant.TABLE_RESTAURANTS, values, DBConstant.TBL_COLUMN_ID+"='"+id+"'", null);
//				} else {
//					value = (int)mdb.insert(DBConstant.TABLE_RESTAURANTS, null, values);
//				}
//				mdb.close();
//			}
//            return value;
//		}
//	}
//
//	synchronized public void addMultipleRestaurants(int listId, List<Restaurant> restList) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
//			if (mdb != null) {
//				mdb.beginTransaction();
//
//				for (Restaurant rest : restList) {
//					ContentValues values = new ContentValues();
//					values.put(DBConstant.RESTAURANTS_NAME, rest.getName());
//					values.put(DBConstant.RESTAURANTS_LIST_ID, listId);
//
//					if (rest.hasValidBusiness()) {
//						values.put(DBConstant.RESTAURANTS_IS_ADDED_BY_ME, 0);
//						values.put(DBConstant.RESTAURANTS_YELP_ID, rest.mInfo.getId());
//						values.put(DBConstant.RESTAURANTS_IMAGE_URL, rest.mInfo.getImageUrl());
//						values.put(DBConstant.RESTAURANTS_PRICE, rest.mInfo.getPrice());
//						values.put(DBConstant.RESTAURANTS_RAITING, rest.mInfo.getRating());
//						values.put(DBConstant.RESTAURANTS_REVIEW_COUNT, rest.mInfo.getReviewCount());
//						values.put(DBConstant.RESTAURANTS_ADDRESS1, rest.mInfo.getLocation().getAddress1());
//						values.put(DBConstant.RESTAURANTS_CITY, rest.mInfo.getLocation().getCity());
//						values.put(DBConstant.RESTAURANTS_STATE, rest.mInfo.getLocation().getState());
//						values.put(DBConstant.RESTAURANTS_ZIPCODE, rest.mInfo.getLocation().getZipCode());
//						values.put(DBConstant.RESTAURANTS_COUNTRY, rest.mInfo.getLocation().getCountry());
//						values.put(DBConstant.RESTAURANTS_LATITUDE, rest.mInfo.getCoordinates().getLatitude());
//						values.put(DBConstant.RESTAURANTS_LONGITUDE, rest.mInfo.getCoordinates().getLongitude());
//					}
//					else {
//						values.put(DBConstant.RESTAURANTS_IS_ADDED_BY_ME, 1);
//					}
//
//					mdb.insert(DBConstant.TABLE_RESTAURANTS, null, values);
//				}
//
//				mdb.setTransactionSuccessful();
//				mdb.endTransaction();
//				mdb.close();
//			}
//		}
//	}
//
//	synchronized public ArrayList<Restaurant> getAllRestaurants() {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			ArrayList<Restaurant> list = new ArrayList<>();
//			SQLiteDatabase mdb = mdbhelper.getReadableDatabase();
//			if (mdb != null) {
//				String sql = String.format(Locale.getDefault(), "SELECT * FROM %s WHERE %s<>0 ORDER BY %s DESC",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.TBL_COLUMN_ID, DBConstant.TBL_COLUMN_ID);
//				Cursor cursor = mdb.rawQuery(sql, null);
//				while (cursor.moveToNext()) {
//					Restaurant item = new Restaurant();
//					item.id = cursor.getInt(cursor.getColumnIndex(DBConstant.TBL_COLUMN_ID));
//					item.name = cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_NAME));
//					item.listId = cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_LIST_ID));
//
//					item.isAddedByMe = cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_IS_ADDED_BY_ME)) > 0;
//					if (item.isAddedByMe) {
//						item.mInfo = null;
//					} else {
//						item.mInfo = new Business();
//
//						item.mInfo.setId(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_YELP_ID)));
//						item.mInfo.setName(item.name);
//						item.mInfo.setImageUrl(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_IMAGE_URL)));
//						item.mInfo.setPrice(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_PRICE)));
//						item.mInfo.setRating(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_RAITING)));
//						item.mInfo.setReviewCount(cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_REVIEW_COUNT)));
//
//						Location location = new Location();
//						location.setAddress1(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_ADDRESS1)));
//						location.setCity(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_CITY)));
//						location.setState(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_STATE)));
//						location.setZipCode(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_ZIPCODE)));
//						location.setCountry(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_COUNTRY)));
//						item.mInfo.setLocation(location);
//
//						Coordinates coord = new Coordinates();
//						coord.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_LATITUDE)));
//						coord.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_LONGITUDE)));
//						item.mInfo.setCoordinates(coord);
//					}
//
//					item.isSelected = false;
//
//					list.add(item);
//				}
//				cursor.close();
//				mdb.close();
//			}
//			return list;
//		}
//	}
//
//	synchronized public ArrayList<Restaurant> getRestaurantsByYelpId(String yelpId) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			ArrayList<Restaurant> list = new ArrayList<>();
//			SQLiteDatabase mdb = mdbhelper.getReadableDatabase();
//			if (mdb != null) {
//				String sql = String.format("SELECT * FROM %s WHERE %s='%s' ORDER BY %s DESC",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.RESTAURANTS_YELP_ID, yelpId, DBConstant.TBL_COLUMN_ID);
//
//				Cursor cursor = mdb.rawQuery(sql, null);
//				while (cursor.moveToNext()) {
//					Restaurant item = new Restaurant();
//					item.id = cursor.getInt(cursor.getColumnIndex(DBConstant.TBL_COLUMN_ID));
//					item.name = cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_NAME));
//					item.listId = cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_LIST_ID));
//
//					item.isAddedByMe = cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_IS_ADDED_BY_ME)) > 0;
//					if (item.isAddedByMe) {
//						item.mInfo = null;
//					} else {
//						item.mInfo = new Business();
//
//						item.mInfo.setId(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_YELP_ID)));
//						item.mInfo.setName(item.name);
//						item.mInfo.setImageUrl(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_IMAGE_URL)));
//						item.mInfo.setPrice(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_PRICE)));
//						item.mInfo.setRating(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_RAITING)));
//						item.mInfo.setReviewCount(cursor.getInt(cursor.getColumnIndex(DBConstant.RESTAURANTS_REVIEW_COUNT)));
//
//						Location location = new Location();
//						location.setAddress1(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_ADDRESS1)));
//						location.setCity(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_CITY)));
//						location.setState(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_STATE)));
//						location.setZipCode(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_ZIPCODE)));
//						location.setCountry(cursor.getString(cursor.getColumnIndex(DBConstant.RESTAURANTS_COUNTRY)));
//						item.mInfo.setLocation(location);
//
//						Coordinates coord = new Coordinates();
//						coord.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_LATITUDE)));
//						coord.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBConstant.RESTAURANTS_LONGITUDE)));
//						item.mInfo.setCoordinates(coord);
//					}
//
//					item.isSelected = false;
//
//					list.add(item);
//				}
//				cursor.close();
//				mdb.close();
//			}
//			return list;
//		}
//	}
//
//	// delete restaurant
//	synchronized public void deleteRestaurant(int id) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
//			if (mdb != null) {
//				String sql_str = String.format(Locale.getDefault(), "DELETE FROM %s WHERE %s=%d",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.TBL_COLUMN_ID, id);
//
//				mdb.execSQL(sql_str);
//				mdb.close();
//			}
//		}
//	}
//
//	synchronized public void deleteRestaurantByYelpId_ListId(String yelpId, int listId) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
//			if (mdb != null) {
//				String sql_str = String.format(Locale.getDefault(), "DELETE FROM %s WHERE %s='%s' AND %s=%d",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.RESTAURANTS_YELP_ID, yelpId, DBConstant.RESTAURANTS_LIST_ID, listId);
//
//				mdb.execSQL(sql_str);
//				mdb.close();
//			}
//		}
//	}
//
//	// delete restaurants of one list
//	synchronized public void deleteRestaurantList(int listId) {
//		synchronized (DBOpenHelper.DB_LOCK) {
//			SQLiteDatabase mdb = mdbhelper.getWritableDatabase();
//			if (mdb != null) {
//				String sql_str = String.format(Locale.getDefault(), "DELETE FROM %s WHERE %s=%d",
//						DBConstant.TABLE_RESTAURANTS, DBConstant.RESTAURANTS_LIST_ID, listId);
//
//				mdb.execSQL(sql_str);
//				mdb.close();
//			}
//		}
//	}
}
