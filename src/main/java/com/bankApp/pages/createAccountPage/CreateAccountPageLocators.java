package com.bankApp.pages.createAccountPage;

import org.openqa.selenium.By;

public class CreateAccountPageLocators {

    public static final By firstName = By.xpath("//input[@id='firstName']");
    public static final By lastName = By.xpath("//input[@id='lastName']");
    public static final By emailID = By.xpath("//input[@id='email']");
    public static final By selectGender = By.xpath("//select[@id='gender']");
    public static final By phoneNumber = By.xpath("//input[@id='phoneNumber']");
    public static final By alternatePhoneNumber = By.xpath("//input[@id='alternatePhoneNumber']");
    public static final By address = By.xpath("//input[@id='address']");
    public static final By state = By.xpath("//select[@id='state']");
    public static final By city = By.xpath("//select[@id='city']");
    public static final By zip = By.xpath("//input[@id='zip']");
    public static final By accountType = By.xpath("//select[@id='accountType']");
    public static final By uploadProfilePic = By.xpath("//input[@id='Upload-Picture']");
    public static final By uploadID = By.xpath("//input[@id='Upload-ID']");
    public static final By termsAndConditionsCheckBox = By.xpath("//input[@id='terms']");
    public static final By submitFormButton = By.xpath("//button[contains(text(),'Submit Form')]");

}
