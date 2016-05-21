package com.unj.log4j.external_file_test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogWithProperties {
	static Logger logger = Logger.getLogger(LogWithProperties.class);

	public static void show()
	{
		logger.info("hehe");
	}
	public static void main(String args[]) {
		PropertyConfigurator.configure("src/log4jConfig.properties");
		logger.debug("Here is some DEBUG");
		logger.info("Here is some INFO");
		logger.warn("Here is some WARN");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");
		show();
	}
}
