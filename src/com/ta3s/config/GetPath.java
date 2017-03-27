package com.ta3s.config;

import java.io.IOException;
import java.util.Properties;

public class GetPath {
	static Properties prop;	
	public static  void readproperty(){
		prop=new Properties();
		
		try {
			prop.load(ClassLoader.getSystemResourceAsStream("config.properties"));
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getBasePath(){
		readproperty();
		return prop.getProperty("baseFilePath");
	}
}
