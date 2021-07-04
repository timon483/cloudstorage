package com.udacity.jwdnd.course1.cloudstorage;

import org.apache.ibatis.annotations.Delete;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logoutButton")
    WebElement logoutButton;

    @FindBy(id = "nav-notes")
    WebElement noteTab;

    @FindBy(id = "note-id")
    WebElement noteId;

    @FindBy(id = "note-title")
    WebElement noteTitle;

    @FindBy(id = "note-description")
    WebElement noteDescription;

    @FindBy(id = "saveNotes")
    WebElement saveNotesButton;

    @FindBy(id = "addNewNoteButton")
    WebElement addNewNoteButton;

    @FindBy(xpath = "//*[@id='addedNoteTitle']")
    WebElement addedNoteTitle;

    @FindBy(xpath = "//*[@id='addedNoteDescription']")
    WebElement addedNoteDescription;

    @FindBy(xpath = "//*[@id='editNoteButton']")
    WebElement editNoteButton;

    @FindBy(id = "nav-credentials-tab")
    WebElement credentialsTab;

    @FindBy(id = "credential-url")
    WebElement credentialURL;

    @FindBy(id = "credential-username")
    WebElement credentialUsername;

    @FindBy(id = "credential-password")
    WebElement credentialPassword;

    @FindBy(id = "saveCredential")
    WebElement saveCredentialButton;

    @FindBy(id = "addCredentialButton")
    WebElement addCredentialButton;







    private final WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addNewNote(String noteTitle, String noteDescription){
        this.clickNoteTab();
        this.clickAddNoteBtn();
        this.addTextToNewNote(noteTitle, noteDescription);

    }

    public void clickNoteTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteTab);
    }

    public void clickAddNoteBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNewNoteButton);
    }

    public void addTextToNewNote(String title, String description) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveNotesButton);
    }

    public String getNoteTitle(){
        return this.addedNoteTitle.getAttribute("innerHTML");
    }

    public String getNoteDescription() {return this.addedNoteDescription.getAttribute("innerHTML") ;}

    public void clickEditNoteBtn(String id, String title, String description) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + id + "';", this.editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editNoteButton);
    }


    public void clickCredentialsTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialsTab);

    }

    public void addNewCredential(String url, String username, String password){

        this.clickCredentialsTab();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredentialButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.credentialURL);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.credentialPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveCredentialButton);

    }

    public void clieckEditCredentialButton(String id, String url, String username, String password) {
        WebElement editNoteButton = driver.findElement(By.id("editCredButton" + id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + id + "';", editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", editNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editNoteButton);
    }

    String getPasswordFromEditing(){
        return driver.findElement(By.id("credential-password")).getAttribute("value");
    }

}
