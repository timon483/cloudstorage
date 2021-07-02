package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    WebElement inputLastName;

    @FindBy (id = "inputUsername")
    WebElement inputUsername;

    @FindBy(id = "inputPassword")
    WebElement inputPassword;

    @FindBy(id = "signupButton")
    WebElement submitButton;

    public SignupPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void singUp(String firstname, String lastname, String username, String password){
        this.inputFirstName.sendKeys(firstname);
        this.inputLastName.sendKeys(lastname);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();

    }


}
