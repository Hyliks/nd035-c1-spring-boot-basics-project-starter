package com.udacity.jwdnd.course1.cloudstorage.selenium;

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
public class SignupTestSuite {
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
    public void testUserSignupValid() {
        String username = "test";
        String password = "whatabadpassword";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        List<WebElement> success = driver.findElements(By.id("signup-success"));

        assertEquals(1,success.size());
    }

    @Test
    public void testUserSignupWithRegisteredUsername() {
        String username = "test1";
        String password = "whatabadpassword";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        List<WebElement> success = driver.findElements(By.id("signup-success"));

        assertEquals(1,success.size());

        signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        List<WebElement> error = driver.findElements(By.id("signup-error"));

        assertEquals(1,error.size());
        assertEquals("The username already exists.",error.get(0).getText());
    }
}
