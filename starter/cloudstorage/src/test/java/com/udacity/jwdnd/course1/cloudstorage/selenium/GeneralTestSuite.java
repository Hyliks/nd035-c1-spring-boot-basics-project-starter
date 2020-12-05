package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.selenium.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneralTestSuite {
    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = baseURL = "http://localhost:" + port;
    }

    @Test
    public void testUnauthorizedAccessToHome() {
        driver.get(baseURL + "/home");

        assertEquals(baseURL + "/login",driver.getCurrentUrl());
    }

    @Test
    public void testUserAuthorizationWorkflow() {
        String username = "test";
        String password = "whatabadpassword";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        assertEquals(baseURL + "/home",driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        assertEquals(baseURL + "/login",driver.getCurrentUrl());

        driver.get(baseURL + "/home");

        assertEquals(baseURL + "/login",driver.getCurrentUrl());
    }
}
