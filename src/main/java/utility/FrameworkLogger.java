package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkLogger {

    private static final Logger logger =
            LoggerFactory.getLogger(FrameworkLogger.class);

    private static final ThreadLocal<StringBuilder> testLogs =
            ThreadLocal.withInitial(StringBuilder::new);

    public static void info(String message) {

        logger.info(message);

        testLogs.get()
                .append("INFO  - ")
                .append(message)
                .append(System.lineSeparator());
    }

    public static void testStart(String testName) {

        String message =
                "========== START TEST: " + testName + " ==========";

        logger.info(message);

        testLogs.get()
                .append("INFO  - ")
                .append(message)
                .append(System.lineSeparator());
    }

    public static void testEnd(String testName) {

        String message =
                "========== END TEST: " + testName + " ==========";

        logger.info(message);

        testLogs.get()
                .append("INFO  - ")
                .append(message)
                .append(System.lineSeparator());
    }

    public static void apiResponse(int statusCode) {

        String message =
                "API Response Status Code: " + statusCode;

        logger.info(message);

        testLogs.get()
                .append("INFO  - ")
                .append(message)
                .append(System.lineSeparator());
    }

    public static void error(String message) {

        logger.error(message);

        testLogs.get()
                .append("ERROR - ")
                .append(message)
                .append(System.lineSeparator());
    }

    public static void error(String message, Throwable throwable) {

        logger.error(message, throwable);

        testLogs.get()
                .append("ERROR - ")
                .append(message)
                .append(System.lineSeparator());

        testLogs.get()
                .append("Exception: ")
                .append(throwable.getMessage())
                .append(System.lineSeparator());
    }

    public static String getTestLogs() {
        return testLogs.get().toString();
    }

    public static void clearTestLogs() {
        testLogs.get().setLength(0);
    }
}