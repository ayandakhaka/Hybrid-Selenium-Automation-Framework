
package api.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

import utility.ConfigReader;

public final class ApiRequestConfig {

    private static final int CONNECTION_TIMEOUT =
            Integer.getInteger("api.connection.timeout", 10000);

    private static final int SOCKET_TIMEOUT =
            Integer.getInteger("api.socket.timeout", 15000);

    private static final RestAssuredConfig REST_ASSURED_CONFIG =
            RestAssuredConfig.config()
                    .httpClient(
                            HttpClientConfig.httpClientConfig()
                                    .setParam(
                                            "http.connection.timeout",
                                            CONNECTION_TIMEOUT
                                    )
                                    .setParam(
                                            "http.socket.timeout",
                                            SOCKET_TIMEOUT
                                    )
                    );

    private ApiRequestConfig() {
        // Prevent instantiation
    }

    public static RequestSpecification getRequestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(
                        ConfigReader.getProperty("api.base.url")
                )
                .setConfig(REST_ASSURED_CONFIG)
                .build();
    }

    public static int getConnectionTimeout() {
        return CONNECTION_TIMEOUT;
    }

    public static int getSocketTimeout() {
        return SOCKET_TIMEOUT;
    }
}
