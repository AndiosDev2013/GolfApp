package com.app.golfapp.database;

public class DBConstant {
	/*
	 * Common
	 */
	protected static final String TBL_COLUMN_ID = "id";

	/* Related DB */
	public static final String DATABASE_NAME = "restaurant_rolulette.db";
	public static final int DATABASE_VERSION = 1;
	

	public static final String CREATE_DB_SQL_PREFIX = "CREATE TABLE IF NOT EXISTS ";
	public static final String DELETE_DB_SQL_PREFIX = "DROP TABLE IF EXISTS ";

	/*
	 *  DB Tables Name
	 */
	public static final String TABLE_RESTAURANTS = "restaurants";

	/*
	 * Restaurants
	 */
	public static final String RESTAURANTS_NAME = "name";
	public static final String RESTAURANTS_LIST_ID = "list_id";

	public static final String RESTAURANTS_IS_ADDED_BY_ME = "is_added_by_me";
	public static final String RESTAURANTS_YELP_ID = "yelp_id";
	public static final String RESTAURANTS_IMAGE_URL = "image_url";
	public static final String RESTAURANTS_PRICE = "price";
	public static final String RESTAURANTS_RAITING = "rating";
	public static final String RESTAURANTS_REVIEW_COUNT = "review_count";
	public static final String RESTAURANTS_ADDRESS1 = "address1";
	public static final String RESTAURANTS_CITY = "city";
	public static final String RESTAURANTS_STATE = "state";
	public static final String RESTAURANTS_ZIPCODE = "zipcode";
	public static final String RESTAURANTS_COUNTRY = "country";
	public static final String RESTAURANTS_LATITUDE = "latitude";
	public static final String RESTAURANTS_LONGITUDE = "longitude";
}
