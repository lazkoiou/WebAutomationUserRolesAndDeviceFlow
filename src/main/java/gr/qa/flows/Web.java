package gr.qa.flows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Web implements DeviceInterface {

    private final static Logger logger = LogManager.getLogger(Web.class);

    @Override
    public void login(String username, String password) {
        logger.info("Logging with Web implementation");
        logger.info("Log in with username: '" + username + "', password: '" + password + "'");
    }

}
