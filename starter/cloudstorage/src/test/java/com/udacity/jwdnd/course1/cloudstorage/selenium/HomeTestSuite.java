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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeTestSuite {

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

        String username = "test";
        String password = "whatabadpassword";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @Test
    public void testCreateNote() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("note");
        home.addNote("123","1234");
        assertEquals(true,home.isNotePresent("123","1234"));
    }

    @Test
    public void testCreateAndEditNote() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("note");
        home.addNote("123","1234");
        assertEquals(true,home.isNotePresent("123","1234"));
        home.editNote(0,"321","4321");
        assertEquals(true,home.isNotePresent("321","4321"));

    }

    @Test
    public void testCreateAndDeleteNote() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("note");
        home.addNote("123","1234");
        assertEquals(true,home.isNotePresent("123","1234"));
        home.deleteNote(0);

        WebDriverWait block = new WebDriverWait(driver,10);
        block.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTable")));
        driver.switchTo().activeElement();

        assertEquals(false,home.isNotePresent("123","1234"));

    }

    @Test
    public void testCreateCredential() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("credential");
        home.addCredential("123","1234","4321");
        assertEquals(true,home.isCredentialPresent("123","1234","4321"));
    }

    @Test
    public void testCreateAndEditCredential() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("credential");
        home.addCredential("123","1234","4321");
        assertEquals(true,home.isCredentialPresent("123","1234","4321"));

        String encodedPassword = home.getEncodedPassword(0);

        home.editCredential(0,"321","4321","5432","4321");

        assertEquals(false,home.isCredentialPresent("321","4321",encodedPassword));
    }

    @Test
    public void testCreateAndDeleteCredential() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.changeTab("credential");
        home.addCredential("123","1234","4321");
        assertEquals(true,home.isCredentialPresent("123","1234","4321"));
        String encodedPassword = home.getEncodedPassword(0);
        home.deleteCredential(0);
        assertEquals(false,home.isCredentialPresent("123","1234",encodedPassword));

    }
}
