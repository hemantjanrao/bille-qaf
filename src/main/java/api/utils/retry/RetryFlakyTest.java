package api.utils.retry;

import api.utils.properties.Property;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFlakyTest implements IRetryAnalyzer {

    /** Maximum retry count of failed tests, defaults to 1. */
    static final int MAX_RETRY_COUNT =
            Property.MAX_RETRY_COUNT.getIntWithDefault(1);

    private int retryCount = 0;

    public boolean retry(ITestResult result) {
        return retryCount++ < MAX_RETRY_COUNT;
    }
}