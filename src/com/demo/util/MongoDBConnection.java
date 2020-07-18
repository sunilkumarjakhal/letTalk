package com.demo.util;

import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;


public class MongoDBConnection {

	public static MongoClient mongoDB;
	public static String db_name;
	public static String db_user;
	public static String db_password;
	public static DB db = null;
	public static boolean IS_DB_CREDENTIAL_ENABLE = true;

	public static boolean makeDBConnection(String ip, int port) {
		boolean isOk = false;

		try {
			if (IS_DB_CREDENTIAL_ENABLE) {
				MongoCredential credential = MongoCredential.createCredential(ConstantConfig.DB_USER, ConstantConfig.DB_NAME,
						ConstantConfig.DB_PASSWORD.toCharArray());
				mongoDB = new MongoClient(new ServerAddress(ip, port), Arrays.asList(credential));
			} else {
				mongoDB = new MongoClient(ip, MongoClientOptions.builder().socketTimeout(100000).connectTimeout(100000)
						.connectionsPerHost(5000).maxConnectionIdleTime(300000).maxWaitTime(300000).build());
			}

			// Builder o = MongoClientOptions.builder().connectTimeout(3000);
			// mongoDB = new MongoClient(new ServerAddress(ip, port),
			// o.connectionsPerHost(2000).maxConnectionIdleTime(300000).maxWaitTime(300000).build());

			db = mongoDB.getDB(ConstantConfig.DB_NAME);
			System.out.println("db set");
	
			isOk = !mongoDB.getConnectPoint().isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}

	public static boolean isConnectionAlive() {
		DBObject ping = new BasicDBObject("ping", "1");
		try {
			MongoDBConnection.mongoDB.getDB(ConstantConfig.DB_NAME).command(ping);
			return true;
		} catch (MongoException e) {
			return false;
		}
	}

	public static MongoClient getMongoClient() {

		return mongoDB;

	}

	public static DB getDB() {
		return db;
	}

	public static boolean stopMongoClient() {
		if (mongoDB != null)
			mongoDB.close();
		return true;
	}
}