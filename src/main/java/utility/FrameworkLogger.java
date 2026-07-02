package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkLogger {
	
	private static final Logger logger =
            LoggerFactory.getLogger(FrameworkLogger.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void testStart(String testName) {
        logger.info("========== START TEST: {} ==========", testName);
    }

    public static void testEnd(String testName) {
        logger.info("========== END TEST: {} ==========", testName);
    }

    public static void apiResponse(int statusCode) {
        logger.info("API Response Status Code: {}", statusCode);
    }
    
    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
