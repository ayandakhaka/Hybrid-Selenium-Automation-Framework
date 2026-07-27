package utility;

import io.qameta.allure.Attachment;

public class AllureAttachment {

    @Attachment(
        value = "Execution Log",
        type = "text/plain"
    )
    public static String attachExecutionLog() {

        return FrameworkLogger.getTestLogs();
    }
}