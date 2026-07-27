package utility;

import io.qameta.allure.Attachment;

public class AllureApiAttachment {

    @Attachment(
            value = "API Request",
            type = "text/plain"
    )
    public static String attachRequest(String request) {
        return request;
    }

    @Attachment(
            value = "API Response",
            type = "application/json"
    )
    public static String attachResponse(String response) {
        return response;
    }
}