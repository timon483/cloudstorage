package com.udacity.jwdnd.course1.cloudstorage;

import org.apache.ibatis.annotations.Delete;
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

    public void clickSaveNoteButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveNotesButton);
    }

    public void addTextToNewNote(String title, String description) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveNotesButton);
    }

    public String getNoteTitle(){
        return this.addedNoteTitle.getAttribute("innerHTML");
    }


}
