package com.demo.util;

public class ConstantConfig {

	public static ConstantConfig constantConfig;

	public static String DB_IP = "localhost";
	public static int DB_PORT = 27017;
	public static String DB_NAME = "letsTalkDB";
	public static String DB_USER = "letstalk";
	public static String DB_PASSWORD = "letstalk";
	public static String DOWNLOAD_PATH = "D:\\files\\";

	public ConstantConfig getInstance() {

		if (constantConfig == null) {
			constantConfig = new ConstantConfig();
		}
		return constantConfig;

	}

}
