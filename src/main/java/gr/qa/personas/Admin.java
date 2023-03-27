package gr.qa.personas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Admin extends Actor {

    private final static Logger logger = LogManager.getLogger(Admin.class);


    public void deletePost() {
        logger.info("Deleting post...");
    }

}
