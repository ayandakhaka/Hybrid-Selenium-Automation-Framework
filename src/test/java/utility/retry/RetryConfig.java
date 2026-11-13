package utility.retry;

public final class RetryConfig {

    private RetryConfig() {
    }

    public static final int MAX_RETRIES =
            Math.max(0, Integer.getInteger("retry.count", 2));

    public static final boolean RETRY_ENABLED =
            Boolean.parseBoolean(
                    System.getProperty("retry.enabled", "true")
            );

    public static final long RETRY_DELAY_MS =
            Math.max(0L, Long.getLong("retry.delay.ms", 1000L));

    public static final boolean RETRY_UI =
            Boolean.parseBoolean(
                    System.getProperty("retry.ui", "true")
            );

    public static final boolean RETRY_API =
            Boolean.parseBoolean(
                    System.getProperty("retry.api", "true")
            );
}