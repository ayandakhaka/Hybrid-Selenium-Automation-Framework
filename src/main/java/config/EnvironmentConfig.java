
package config;

public class EnvironmentConfig {

    private final String environment;
    private final String uiBaseUrl;
    private final String apiBaseUrl;
    private final String browser;
    private final boolean headless;

    public EnvironmentConfig(
            String environment,
            String uiBaseUrl,
            String apiBaseUrl,
            String browser,
            boolean headless) {

        this.environment = environment;
        this.uiBaseUrl = uiBaseUrl;
        this.apiBaseUrl = apiBaseUrl;
        this.browser = browser;
        this.headless = headless;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getUiBaseUrl() {
        return uiBaseUrl;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isHeadless() {
        return headless;
    }
}
