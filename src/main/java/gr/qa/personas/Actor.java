package gr.qa.personas;

import gr.qa.pages.HomePage;
import gr.qa.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Actor {

    private final static Logger logger = LogManager.getLogger(Actor.class);

    public void uploadImage() {
        logger.info("Uploading image...");
    }

    public void editPost() {
        logger.info("Editing post...");
    }

    public void writePost() {
        logger.info("Writing post...");
    }

    public String readNotification() {
        return "notificationTextExample";
    }

    public void login(String username, String password) {
        new LoginPage().login(username, password);
    }

}
