package api.tests;

import api.utils.listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

@Listeners({TestListener.class})
@Test(groups = "base-api")
public abstract class BaseAPITest {

    protected final Logger logger = LogManager.getLogger(this);

}
