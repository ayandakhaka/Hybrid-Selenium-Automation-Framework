package utility;

import io.qameta.allure.Allure;

public class AllureApiHelper {

    public static void attachRequest(
            String method,
            String url,
            String headers,
            String body) {

        String requestDetails =
                "Method: " + method + "\n"
                + "URL: " + url + "\n\n"
                + "Headers:\n"
                + headers + "\n\n"
                + "Request Body:\n"
                + body;

        Allure.addAttachment(
                "API Request",
                "text/plain",
                requestDetails
        );
    }


    public static void attachResponse(
            int statusCode,
            String headers,
            String body) {

        String responseDetails =
                "Status Code: "
                + statusCode
                + "\n\n"
                + "Headers:\n"
                + headers
                + "\n\n"
                + "Response Body:\n"
                + body;

        Allure.addAttachment(
                "API Response",
                "text/plain",
                responseDetails
        );
    }
}