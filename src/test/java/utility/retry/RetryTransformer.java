
package utility.retry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            Constructor testConstructor,
            Method testMethod) {

        if (testClass == null && testMethod != null) {
            testClass = testMethod.getDeclaringClass();
        }

        if (testClass == null) {
            return;
        }

        String packageName =
                testClass.getPackage().getName();

        boolean isUiTest =
                packageName.startsWith("ui.tests");

        boolean isApiTest =
                packageName.startsWith("api.tests");

        boolean enableRetry = false;

        // Automatically enable retry for UI tests
        if (isUiTest && RetryConfig.RETRY_UI) {
            enableRetry = true;
        }

        // API retries require explicit opt-in
        if (isApiTest && RetryConfig.RETRY_API) {

            boolean methodIsSafe =
                    testMethod != null
                    && testMethod.isAnnotationPresent(RetrySafe.class);

            boolean classIsSafe =
                    testClass.isAnnotationPresent(RetrySafe.class);

            enableRetry = methodIsSafe || classIsSafe;
        }

        if (enableRetry && RetryConfig.RETRY_ENABLED) {

            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
