package com.demo.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class Util {

	public HashMap<String, String> getCountryList() throws Exception {

		HashMap<String, String> countryList = new HashMap<String, String>();

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_country_list_data");

			DBCursor cursor = table.find();
			BasicDBObject oneDetail;

			int id = 1;
			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();
				BasicDBList countryLi = (BasicDBList) oneDetail.get("country");

				for (Object li : countryLi) {
					countryList.put(li.toString(), li.toString());
				}

				id++;
			}
			System.out.println("country List data : " + countryList);
			return countryList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countryList;
	}

	public Map<String, String> getStateList(String country) throws Exception {

		Map<String, String> stateList = new LinkedHashMap<String, String>();

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_state_list_data");

			BasicDBObject query = new BasicDBObject();
			query.put("country", country);

			DBCursor cursor = table.find(query);

			BasicDBObject oneDetail;
			BasicDBList allState;
			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();
				allState = (BasicDBList) oneDetail.get("state_list");
				for (Object state : allState) {

					stateList.put(state.toString(), state.toString());
				}

			}
			System.out.println("state List data : " + stateList);
			return stateList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateList;
	}

	public Map<String, String> getCityList(String state) throws Exception {

		Map<String, String> cityList = new LinkedHashMap<String, String>();

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_city_list_data");

			BasicDBObject query = new BasicDBObject();
			query.put("state", state);

			DBCursor cursor = table.find(query);

			BasicDBObject oneDetail;
			BasicDBList allCity;
			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();
				allCity = (BasicDBList) oneDetail.get("city_list");
				for (Object city : allCity) {

					cityList.put(city.toString(), city.toString());
				}

				System.out.println("City List data : " + cityList);
			}
			return cityList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

	public BasicDBList findFriendList(String username) {

		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_friends_list_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", username);

			DBCursor friends = table.find(query);
			BasicDBList dbList = new BasicDBList();
			while (friends.hasNext()) {
				BasicDBObject oneDetail = (BasicDBObject) friends.next();
				dbList = (BasicDBList) oneDetail.get("friend_list");

			}

			return dbList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean isAccountActivate(String name) {
		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", name);

			DBCursor cursor = table.find(query);

			while (cursor.hasNext()) {
				BasicDBObject oneDetail = (BasicDBObject) cursor.next();
				return oneDetail.getBoolean("is_account_activate");

			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
