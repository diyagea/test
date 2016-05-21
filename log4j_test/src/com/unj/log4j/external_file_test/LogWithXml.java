package com.unj.log4j.external_file_test;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LogWithXml {
	static Logger logger = Logger.getLogger(LogWithXml.class);
	   public static void main(String args[]) {
	      DOMConfigurator.configure("src/log4jConfig.xml");
	      logger.debug("Here is some DEBUG");
	      logger.info("Here is some INFO");
	      logger.warn("Here is some WARN");
	      logger.error("Here is some ERROR");
	      logger.fatal("Here is some FATAL");
	   }
}
