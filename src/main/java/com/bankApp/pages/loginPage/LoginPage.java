package com.bankApp.pages.loginPage;

import com.bankApp.base.BaseClass;
import com.bankApp.pages.dashboardPage.DashboardPageLocators;
import com.bankApp.util.ActionUtils;
import com.bankApp.util.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils wait;
    private ActionUtils action;

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoginPage.class);

    //assign driver to call from test class
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.action = new ActionUtils(driver);
    }

    //method will add username, password and click on signin
    public void signIn(String userName, String password){

        WebElement userNameElement = action.getElement(LoginPageLocators.enterUserName);
        WebElement passwordElement = action.getElement(LoginPageLocators.enterPassword);
        WebElement signInElement = action.getElement(LoginPageLocators.signInButton);

        userNameElement.click();
        userNameElement.clear();
        userNameElement.sendKeys(userName);

        passwordElement.click();
        passwordElement.clear();
        passwordElement.sendKeys(password);

        signInElement.click();

    }

    //method will fetch valid values from property file and login (Valid login with UserName and pass)
    public void validSignInWithUserName(){
        String userName = BaseClass.getProperty("validUserName");
        logger.info(userName);
        String password = BaseClass.getProperty("validPassword");
        logger.info(password);

        signIn(userName,password);
        logger.info("Sign In button clicked");
        wait.waitForElementToBeVisible(DashboardPageLocators.logOutButton,10);
        wait.waitForPageToLoad(10);
    }

    //method will fetch valid values from property file and login (Valid login with email and pass)
    public void validSignInWithEmail(){
        String emailID = BaseClass.getProperty("validEmailID");
        String password = BaseClass.getProperty("validPassword");

        signIn(emailID,password);
        logger.info("Sign In button clicked");
        wait.waitForElementToBeVisible(DashboardPageLocators.logOutButton,10);
        wait.waitForPageToLoad(10);
    }

    //method will check all invalid signIn scenarios
    public void invalidSignIn(String userNameOrEmailID, String password){
        signIn(userNameOrEmailID,password);
        logger.info("Sign In button clicked");

    }

    //method to chech if login page is opened
    public boolean isLoginPageOpened(){
        boolean loginPageOpen = false ;
        String expectedPageTitle = action.getExpectedPageTitleFromExcel("login");
        String fetchedPageTitle = action.getTitle();
        logger.info("Expected page title: {}",expectedPageTitle);
        logger.info("Fetch page title: {}",fetchedPageTitle);
        if(expectedPageTitle.equalsIgnoreCase(fetchedPageTitle))
        {
            loginPageOpen = true;
        }
        return loginPageOpen;
    }

    //method to check if login is successful
    public boolean isLoginSuccessful(){
        //wait.waitForPageToLoad(10);
        boolean isLoginSucessful = false;
        String expectedTitle = action.getExpectedPageTitleFromExcel("dashboard");
        String fetchedTitle = action.getTitle();
        logger.info("Fetch page title: {}",fetchedTitle);
        if(expectedTitle.equalsIgnoreCase(fetchedTitle))
        {
            isLoginSucessful = true;
        }
        return isLoginSucessful;
    }

    //method will signout the application
    public void logOut(){
        action.clickButton(DashboardPageLocators.logOutButton);
        wait.waitForElementToBeVisible(LoginPageLocators.loginToYourAccountText,10);
    }

    //method will check if alert is present
    public boolean isAlertPresent() {
        try {
            // Try to switch to alert
            Alert alert = driver.switchTo().alert();
            return true; // If alert is present, return true
        } catch (NoAlertPresentException e) {
            return false; // If no alert is present, return false
        }
    }

    //method will handle alert
    public void verifyAlertMessage(){

        wait.waitForAlertToBePresent(10);
        if(isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            logger.info("Fetched text from alert: {}", alertText);
            alert.accept();
        } else {
            logger.info("Alert is not present");
        }

        /*
        String fetchedAlertMessage = driver.switchTo().alert().getText();
        logger.info("Fetched alert Message: {}",fetchedAlertMessage);
        String expectedAlertMessage = action.getConstantFromExcel("loginErrorAlertMessage");
        logger.info("Expected alert Message: {}",expectedAlertMessage);
        if(fetchedAlertMessage.equalsIgnoreCase(expectedAlertMessage))
        {
            return true;
        }
        return false;

         */
    }

    //click ok on alert for login
    public void clickAlertOK(){
        //wait.waitForAlertToBePresent(10);
        driver.switchTo().alert().accept();
        logger.info("Clicked OK in alert");
    }

    //INCOMPLETE
    //method to fetch the error
    public String fetchError(){
        String errorMessage = "";
        return errorMessage;
    }

    //method will clear username and password field
    public void clearUserNameAndPasswordField(){
        action.clickButton(LoginPageLocators.enterUserName);
        action.clearField(LoginPageLocators.enterUserName);
        logger.info("UserName/EmailID field is cleared");
        action.clickButton(LoginPageLocators.enterPassword);
        action.clearField(LoginPageLocators.enterPassword);
        logger.info("Password field is cleared");
    }

    //INCOMPLETE
    //method will check in database if user is visible in mysql database
    public boolean isAccountCreated(){
        return true;
    }
}
