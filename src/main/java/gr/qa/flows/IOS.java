package gr.qa.flows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOS implements DeviceInterface {

    private final static Logger logger = LogManager.getLogger(IOS.class);

    @Override
    public void login(String username, String password) {
        logger.info("Logging with IOS implementation");
        logger.info("Log in with username: '" + username + "', password: '" + password + "'");
    }

}
