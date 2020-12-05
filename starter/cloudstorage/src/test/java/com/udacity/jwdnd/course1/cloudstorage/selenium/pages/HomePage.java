package com.udacity.jwdnd.course1.cloudstorage.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement navFileTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "credentialModal")
    private WebElement credentialModal;

    private WebDriver driver;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void changeTab(String tab) {
        switch (tab) {
            case "note": this.navNoteTab.click(); break;
            case "file": this.navFileTab.click(); break;
            case "credential": this.navCredentialTab.click(); break;
        }
    }

    public void addNote(String title, String description) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addNoteButton);

        WebDriverWait block = new WebDriverWait(driver,10);
        block.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-modal-content")));
        driver.switchTo().activeElement();

        WebElement noteModalNoteTitle = this.driver.findElement(By.name("noteTitle"));
        WebElement noteModalNoteDescription = this.driver.findElement(By.name("noteDescription"));
        WebElement noteModalSubmitButton = this.driver.findElement(By.id("noteSubmit"));

        noteModalNoteTitle.sendKeys(title);
        noteModalNoteDescription.sendKeys(description);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", noteModalSubmitButton);
    }

    public boolean isNotePresent(String title, String description) {
        WebElement tableBody = this.noteTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        for(WebElement row: rows) {
            List<WebElement> tdCols = row.findElements(By.tagName("td"));
            List<WebElement> thCols = row.findElements(By.tagName("th"));

            WebElement weDescription = tdCols.get(2);
            WebElement weTitle = thCols.get(1);

            String foundDesc = weDescription.getText();
            String foundTitle = weTitle.getText();

            if(foundDesc.equals(description)) {
                if(foundTitle.equals(title)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void editNote(Integer index, String newTitle, String newDescription) throws InterruptedException {
        WebElement tableBody = this.noteTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        WebElement editNoteButton = rows.get(index).findElements(By.tagName("td")).get(0).findElement(By.tagName("button"));

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editNoteButton);

        WebDriverWait block = new WebDriverWait(driver,10);
        block.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-modal-content")));
        driver.switchTo().activeElement();

        WebElement noteModalNoteTitle = this.driver.findElement(By.name("noteTitle"));
        WebElement noteModalNoteDescription = this.driver.findElement(By.name("noteDescription"));
        WebElement noteModalSubmitButton = this.driver.findElement(By.id("noteSubmit"));

        noteModalNoteTitle.clear();
        noteModalNoteDescription.clear();

        noteModalNoteTitle.sendKeys(newTitle);
        noteModalNoteDescription.sendKeys(newDescription);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", noteModalSubmitButton);
    }

    public void deleteNote(Integer index) throws InterruptedException {
        WebElement tableBody = this.noteTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        WebElement deleteNoteButton = rows.get(index).findElements(By.tagName("td")).get(1).findElement(By.tagName("form")).findElement(By.id("delete-note"));

        deleteNoteButton.click();

    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addCredentialButton);

        WebDriverWait block = new WebDriverWait(driver,10);
        block.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-modal-content")));
        driver.switchTo().activeElement();

        WebElement credentialModalUrl = this.driver.findElement(By.id("credential-url"));
        WebElement credentialModalUsername = this.driver.findElement(By.id("credential-username"));
        WebElement credentialModalPassword = this.driver.findElement(By.id("credential-password"));
        WebElement credentialModalSubmit = this.driver.findElement(By.id("credentialSubmit"));

        credentialModalUrl.sendKeys(url);
        credentialModalUsername.sendKeys(username);
        credentialModalPassword.sendKeys(password);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", credentialModalSubmit);
    }

    public boolean isCredentialPresent(String url, String username, String password) {
        WebElement tableBody = this.credentialTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        for(WebElement row: rows) {
            List<WebElement> tdCols = row.findElements(By.tagName("td"));
            List<WebElement> thCols = row.findElements(By.tagName("th"));

            WebElement weUrl = thCols.get(1);
            WebElement weUsername = tdCols.get(2);
            WebElement wePassword = tdCols.get(3);

            if(weUrl.getText().equals(url)) {
                if(weUsername.getText().equals(username)) {
                    if(!wePassword.getText().equals(password)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String getEncodedPassword(Integer index) {
        WebElement tableBody = this.credentialTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        List<WebElement> tdCols = rows.get(index).findElements(By.tagName("td"));

        WebElement wePassword = tdCols.get(3);

        return wePassword.getText();
    }

    public void editCredential(Integer index, String newUrl, String newUsername, String newPassword, String oldPassword) throws InterruptedException {
        WebElement tableBody = this.credentialTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        WebElement editCredentialButton = rows.get(index).findElements(By.tagName("td")).get(0).findElement(By.tagName("button"));

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editCredentialButton);

        Thread.sleep(5000);

        WebDriverWait block = new WebDriverWait(driver,10);
        block.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-modal-content")));
        driver.switchTo().activeElement();

        WebElement credentialModalUrl = this.driver.findElement(By.id("credential-url"));
        WebElement credentialModalUsername = this.driver.findElement(By.id("credential-username"));
        WebElement credentialModalPassword = this.driver.findElement(By.id("credential-password"));
        WebElement credentialModalSubmit = this.driver.findElement(By.id("credentialSubmit"));

        assertEquals(oldPassword,credentialModalPassword.getAttribute("value"));

        credentialModalUrl.clear();
        credentialModalUsername.clear();
        credentialModalPassword.clear();

        credentialModalUrl.sendKeys(newUrl);
        credentialModalUsername.sendKeys(newUsername);
        credentialModalPassword.sendKeys(newPassword);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", credentialModalSubmit);
    }


    public void deleteCredential(Integer index) throws InterruptedException {
        WebElement tableBody = this.credentialTable.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        WebElement deleteNoteButton = rows.get(index).findElements(By.tagName("td")).get(1).findElement(By.tagName("form")).findElement(By.id("delete-credential"));

        deleteNoteButton.click();

    }
}
