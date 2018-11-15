package com.compdb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConfigUtil {
	static Properties prop = new Properties();
	public static Logger s_logs = LoggerUtil.logger();

	// load a properties file
	public static String getProperty(String locator) {
		InputStream input;
		String propertyValue = "";
		try {
			input = new FileInputStream(
					getRootDir() + "/src/test/resources/application.properties");
			prop.load(input);
			propertyValue = prop.getProperty(locator);
		} catch (IOException e) {
			s_logs.log(Level.ERROR, "Unable to read properties file", e);
		}
		;
		return propertyValue;
	}

	public static String getRootDir() {
		String propertiesLocation = "";
		String path = ConfigUtil.class.getResource("ConfigUtil.class").getPath();
		path.replace("\\", "/");
		int sourceLocation = path.indexOf("/ComputerDB/");
		if (sourceLocation > 1) {
			propertiesLocation = path.substring(1, sourceLocation);
		}
		propertiesLocation = propertiesLocation + "/ComputerDB";
		return propertiesLocation;
	}
}