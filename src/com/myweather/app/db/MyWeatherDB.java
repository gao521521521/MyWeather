package com.myweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.myweather.app.model.City;
import com.myweather.app.model.County;
import com.myweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyWeatherDB {
	public static final String DB_NAME = "my_weather";

	public static final int VWESION = 1;

	private static MyWeatherDB myWeatherDB;

	private SQLiteDatabase db;

	private MyWeatherDB(Context context) {
		MyWeatherOpenHelper dbHelper = new MyWeatherOpenHelper(context,
				DB_NAME, null, VWESION);
		db = dbHelper.getWritableDatabase();
	}

	public static synchronized MyWeatherDB getInstance(Context context) {
		if (myWeatherDB == null) {
			myWeatherDB = new MyWeatherDB(context);
		}
		return myWeatherDB;
	}

	/**
	 * ��ȫʡ��Ϣ�洢�����ݿ���
	 * 
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ��ж�ȡȫ�����е�ʡ����Ϣ
	 * 
	 * @return
	 */
	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}

		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * ��City��Ϣ�洢�����ݿ���
	 * 
	 * @param city
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * �����ݿ��в��ҳ�ĳʡ�µ����г��е���Ϣ
	 * 
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor != null) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor
						.getColumnIndex("province_id")));
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * ��County���������ݿ���
	 * 
	 * @param county
	 */
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	/**
	 * ����ĳ�����������ص���Ϣ
	 * 
	 * @param cityId
	 * @return
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor != null) {
			County county = new County();
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyName(cursor.getString(cursor
					.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor
					.getColumnIndex("county_code")));
			county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
			list.add(county);
		}
		return list;
	}
}
