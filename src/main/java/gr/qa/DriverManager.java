package gr.qa;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DriverManager {

    private final static Logger logger = LogManager.getLogger(DriverManager.class);

    public static ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<>();

    public static Properties properties = new Properties();
    public static String environment;

    public static EventFiringWebDriver getDriver() {
        return driver.get();
    }

    /**
     * Setup the webdriver
     */
    public static void setDriver() {
        logger.info("Initializing web driver...");
        // System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe"); // to use a specific downloaded chrome driver
        loadProperties();
        String driverName = getBrowser();
        switch (driverName) {
            case "chrome":
                ChromeOptions chromeOptions = setupChromeOptions();
                WebDriverManager.chromedriver().setup();
                DriverManager.driver.set(new EventFiringWebDriver(new ChromeDriver(chromeOptions)));
                break;
            case "chromeMobileView":
                ChromeOptions mobileViewOptions = setupChromeOptions();
                // additional options are needed for chrome mobile view
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                mobileViewOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                WebDriverManager.chromedriver().setup();
                DriverManager.driver.set(new EventFiringWebDriver(new ChromeDriver(mobileViewOptions)));
                break;
        }
        customizeDriver();
        logger.info("The web driver has been initialized.");
    }

    /**
     * Setup the chrome options to pass to the webdriver
     * @return : the chrome options
     */
    public static ChromeOptions setupChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        /* -- Might be needed for Jenkins, keep them here --
        options.addArguments("--no-sandbox");
        if (System.getenv("operatingSystem") != null && System.getenv("operatingSystem").equals("linux")) {
            // Should be enabled for Jenkins but we don't want it when running tests locally (in windows)
            // If we change operating systems this should also change to mention specifically Jenkins
            // options.addArguments("--headless");
            options.addArguments("--disable-software-rasterizer");
        }
        options.addArguments("--disable-dev-shm-usage"); // Should be enabled for Jenkins
        options.addArguments("--remote-debugging-port-9222"); // Should be enabled for Jenkins
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        options.addArguments("enable-automation");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // This option was added to deal with Timed out receiving message from renderer
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-browser-side-navigation");
        */
        return options;
    }

    /**
     * Get the browser we are running our tests
     * @return : browser name
     */
    @Parameters
    public static String getBrowser() {
        String browser;
        ITestContext iTestContext = Reporter.getCurrentTestResult().getTestContext();
        if (iTestContext.getCurrentXmlTest().getParameter("browser") != null) { // if there is a test parameter
            browser = iTestContext.getCurrentXmlTest().getParameter("browser");
        }
        else if (System.getenv("browser") != null) { // if we pass it from Jenkins
            browser = System.getenv("browser");
        }
        else  { // lastly, take it from the properties file
            browser = properties.getProperty("browser");
        }
        logger.info("Browser is: " + browser);
        return browser;
    }

    @Parameters
    public static String getDevice() {
        String device;
        ITestContext iTestContext = Reporter.getCurrentTestResult().getTestContext();
        if (iTestContext.getCurrentXmlTest().getParameter("device") != null) { // if there is a test parameter
            device = iTestContext.getCurrentXmlTest().getParameter("device");
        }
        else if (System.getenv("device") != null) { // if we pass it from Jenkins
            device = System.getenv("device");
        }
        else  { // lastly, take it from the properties file
            device = properties.getProperty("device");
        }
        logger.info("Device is: " + device);
        return device;
    }

    /**
     * Loads property files
     * @param filePath : the path where the property file is located
     */
    public static void loadPropertyFile(String filePath) {
        logger.info("Filepath is: " + filePath);
        InputStream stream;
        try {
            stream = new FileInputStream(filePath);
            properties.load(new InputStreamReader(stream, "iso-8859-7")); // used to read Greek
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("Error loading properties files!");
            throw new RuntimeException();
        }
    }

    /**
     * Load the appropriate properties files, depending on the environment
     */
    public static void loadProperties() {
        logger.info("Loading property files...");
        environment = getEnvironment();
        // depending on the environment load the correct properties file
        if (environment.equals("staging")) {
            // load dynamic data
            loadPropertyFile("src/main/resources/dataStaging.properties");
        }
        else {
            // load dynamic data
            loadPropertyFile("src/main/resources/dataProduction.properties");
        }
        logger.info("Property files were successfully loaded.");
    }

    /**
     * Finds out the environment either from Jenkins or from the xml
     * @return : the environment as a String
     */
    public static String getEnvironment() {
        ITestContext iTestContext = Reporter.getCurrentTestResult().getTestContext();
        if (System.getenv("environment") != null) { // if we pass it from Jenkins
            return System.getenv("environment");
        }
        else { // we run it locally and we pass it through the xml
            return iTestContext.getSuite().getParameter("environment");
        }
    }

    /**
     * Customize the driver
     */
    public static void customizeDriver() {
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Quits the web driver
     */
    public static void tearDownDriver() {
        DriverManager.driver.get().quit();
    }

}
