package utility.retry;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.UnreachableBrowserException;

public final class RetryExceptionClassifier {

    private RetryExceptionClassifier() {
    }

    public static boolean isRetryable(Throwable throwable) {

        if (throwable == null) {
            return false;
        }

        Set<Throwable> visited =
                Collections.newSetFromMap(new IdentityHashMap<>());

        Throwable current = throwable;

        while (current != null && visited.add(current)) {

            if (isTransientFailure(current)) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }

    private static boolean isTransientFailure(Throwable exception) {

        // Selenium transient failures
        if (exception instanceof StaleElementReferenceException
                || exception instanceof TimeoutException
                || exception instanceof NoSuchSessionException
                || exception instanceof NoSuchWindowException
                || exception instanceof UnreachableBrowserException) {

            return true;
        }

        // API/network transient failures
        return exception instanceof SocketTimeoutException
                || exception instanceof ConnectException
                || exception instanceof SocketException
                || exception instanceof EOFException
                || isTransientApiTransportFailure(exception);
    }
    
    private static boolean isTransientApiTransportFailure(
            Throwable exception) {

        String className = exception.getClass().getName();

        return className.equals(
                    "org.apache.http.conn.ConnectTimeoutException")
                || className.equals(
                    "org.apache.http.NoHttpResponseException");
    }
    
    
}