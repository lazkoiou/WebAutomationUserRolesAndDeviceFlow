package gr.qa.flows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlowFactory {

    private final static Logger logger = LogManager.getLogger(FlowFactory.class);

    public DeviceInterface getFlow(String platform) {
        logger.info("Getting flow for " + platform);
        if (platform.equalsIgnoreCase("android")) {
            return new Android();
        }
        else if (platform.equalsIgnoreCase("ios")) {
            return new IOS();
        }
        else if (platform.equalsIgnoreCase("web")) {
            return new Web();
        }
        else {
            throw new RuntimeException("Platform not supported");
        }
    }
}
