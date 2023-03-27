package gr.qa.personas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Contributor extends Actor {

    private final static Logger logger = LogManager.getLogger(Contributor.class);

    @Override
    public void editPost() {
        logger.info("Override edit post from Contributor");
    }

}
