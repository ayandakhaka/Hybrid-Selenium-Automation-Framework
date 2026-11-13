
package utility.retry;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import io.qameta.allure.Allure;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger =
            LoggerFactory.getLogger(RetryAnalyzer.class);

    private final AtomicInteger retryAttempts =
            new AtomicInteger(0);

    @Override
    public boolean retry(ITestResult result) {

        if (!RetryConfig.RETRY_ENABLED) {
            return false;
        }

        Throwable failure = result.getThrowable();

        if (!RetryExceptionClassifier.isRetryable(failure)) {

            logger.info(
                    "Test '{}' will not be retried. Failure is not transient.",
                    result.getName()
            );

            return false;
        }

        int currentAttempt = retryAttempts.get();

        if (currentAttempt >= RetryConfig.MAX_RETRIES) {

            logger.error(
                    "Retry limit reached for test '{}'. Total retries: {}",
                    result.getName(),
                    currentAttempt
            );

            return false;
        }

        int nextAttempt = retryAttempts.incrementAndGet();

        String failureMessage = failure.getClass().getSimpleName()
                + ": " + failure.getMessage();

        String retryMessage = String.format(
                "Retrying test: %s | Attempt: %d/%d | Failure: %s",
                result.getName(),
                nextAttempt,
                RetryConfig.MAX_RETRIES,
                failureMessage
        );

        logger.warn(retryMessage);

        try {
            Allure.addAttachment(
                    "Retry Attempt " + nextAttempt,
                    "text/plain",
                    retryMessage,
                    ".txt"
            );
        } catch (Exception attachmentException) {
            logger.debug(
                    "Could not attach retry details to Allure.",
                    attachmentException
            );
        }

        if (RetryConfig.RETRY_DELAY_MS > 0) {

            try {
                Thread.sleep(RetryConfig.RETRY_DELAY_MS);

            } catch (InterruptedException interruptedException) {

                Thread.currentThread().interrupt();

                logger.warn(
                        "Retry interrupted for test '{}'.",
                        result.getName()
                );

                return false;
            }
        }

        return true;
    }
}
