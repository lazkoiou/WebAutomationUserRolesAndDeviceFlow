package gr.qa.pages;

import gr.qa.flows.FlowFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gr.qa.DriverManager.getDevice;

public class LoginPage extends BasePage {

    private final static Logger logger = LogManager.getLogger(LoginPage.class);

    public void login(String username, String password) {
        FlowFactory flow = new FlowFactory();
        flow.getFlow(getDevice()).login(username, password);
    }

}
