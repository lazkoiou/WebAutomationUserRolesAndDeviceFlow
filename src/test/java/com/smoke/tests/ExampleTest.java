package com.smoke.tests;

import gr.qa.DriverManager;
import gr.qa.pages.HomePage;
import gr.qa.pages.LoginPage;
import gr.qa.personas.Admin;
import gr.qa.personas.Author;
import gr.qa.personas.Contributor;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExampleTest {

    private final static Logger logger = LogManager.getLogger(ExampleTest.class);

    EventFiringWebDriver driver;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeClass
    void initialize() {
        DriverManager.setDriver();
        driver = DriverManager.getDriver();
        loginPage = new LoginPage();
        loginPage.setDriverInitElements(driver);
        homePage = new HomePage();
        homePage.setDriverInitElements(driver);
        logger.info("Starting SmokeTests Suite...");
    }

    @AfterClass
    void tearDown() {
        DriverManager.tearDownDriver();
        logger.info("Finished SmokeTests Suite ...\n");
    }

    @Test
    void adminShouldBeAbleToDelete() {
        logger.info("Running adminShouldBeAbleToDelete test...");
        Admin admin = new Admin();
        admin.login("admin", "admin");
        admin.deletePost();
        assertEquals(admin.readNotification(), "notificationTextExample");
    }

    @Test
    void authorShouldBeAbleToUpload() {
        logger.info("Running authorShouldBeAbleToUpload test...");
        Author author = new Author();
        author.login("author", "author");
        author.uploadImage();
        author.writePost();
        assertEquals(author.readNotification(), "notificationTextExample");
    }

    @Test
    void contributorShouldBeAbleToEdit() {
        logger.info("Running contributorShouldBeAbleToEdit test...");
        Contributor contributor = new Contributor();
        contributor.login("contributor", "contributor");
        contributor.writePost();
        contributor.editPost();
        assertEquals(contributor.readNotification(), "notificationTextExample");
    }

}
