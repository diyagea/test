import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class testLog {
	protected static Logger logger = LogManager.getLogger(testLog.class);
	
	
	public static void main(String[] args) {
		logger.info("info");
		logger.debug("debug");
		logger.warn("warn");
		logger.error("error");
	}
}
