package gr.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for the web pages
 * Contains some basic functions that each page might use
 */
public class BasePage {

    public static WebDriver driver;

    /**
     * Constructor
     * @param driver: webdriver
     */
    public void setDriverInitElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
