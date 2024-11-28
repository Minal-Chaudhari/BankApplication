package com.bankApp.pages.loginPage;

import org.openqa.selenium.By;

public class LoginPageLocators {

    public static final By enterUserName = By.xpath("//input[@placeholder='Username or Email']");
    public static final By enterPassword = By.xpath("//input[@placeholder='Password']");
    public static final By signInButton = By.xpath("//button[contains(text(),'Sign In')]");
    public static final By forgotPasswordLink = By.xpath("//a[contains(text(),'Forgot Password?')]");
    public static final By signUpButton = By.xpath("//a[contains(text(),'Sign Up')]");
    public static final By loginToYourAccountText = By.xpath("//h2[contains(text(),'Login to Your Account')]");

}
