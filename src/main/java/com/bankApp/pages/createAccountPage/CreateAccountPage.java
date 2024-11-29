package com.bankApp.pages.createAccountPage;

import com.bankApp.base.BaseClass;
import com.bankApp.pages.loginPage.LoginPage;
import com.bankApp.pages.loginPage.LoginPageLocators;
import com.bankApp.util.ActionUtils;
import com.bankApp.util.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateAccountPage {


    private WebDriver driver;
    private WaitUtils wait;
    private ActionUtils action;

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(CreateAccountPage.class);

    //assign driver to call from test class
    public CreateAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.action = new ActionUtils(driver);
    }

    //method will insert profile picture and ID
    public void addProfilePicAndID(){
        addProfilePicImage();
        addIDProof();
    }

    //method will submit the form
    public void submitForm(){
        WebElement submitForm = action.getElement(CreateAccountPageLocators.submitFormButton);
        submitForm.click();
    }

    //method will add image as profile pic
    public void addProfilePicImage(){
        String imagePath = BaseClass.getProperty("profilePicImagePath");
        WebElement profilePicElement = action.getElement(CreateAccountPageLocators.uploadProfilePic);
        profilePicElement.sendKeys();
    }

    //method will add image as ID proof
    public void addIDProof(){
        String imagePath = BaseClass.getProperty("idProofImagePath");
        WebElement idProofElement = action.getElement(CreateAccountPageLocators.uploadID);
        idProofElement.sendKeys();
    }

    //INCOMPLETE
    //method will create account for a given user
    public void fillCreateAccountForm(String FirstName,String LastName,String Email,String Gender,String PhoneNumber,String AlternatePhoneNumber,String Address,String State,String City,String Zip,String AccountType){

        WebElement firstNameElement = action.getElement(LoginPageLocators.enterUserName);
        WebElement lastNameElement = action.getElement(LoginPageLocators.enterPassword);
        WebElement emailElement = action.getElement(LoginPageLocators.signInButton);
        WebElement genderElement = action.getElement(LoginPageLocators.enterUserName);
        WebElement phoneNumberElement = action.getElement(LoginPageLocators.enterPassword);
        WebElement alternatePhoneNumberElement = action.getElement(LoginPageLocators.signInButton);
        WebElement addressElement = action.getElement(LoginPageLocators.enterUserName);
        WebElement stateElement = action.getElement(LoginPageLocators.enterPassword);
        WebElement cityElement = action.getElement(LoginPageLocators.signInButton);
        WebElement zipElement = action.getElement(LoginPageLocators.enterPassword);
        WebElement accountTypeElement = action.getElement(LoginPageLocators.signInButton);



    }

    //method will check if create account is opened
    public boolean isCreateAccountPageOpened(){
        boolean createAccountPageOpen = false ;
        String expectedPageTitle = action.getExpectedPageTitleFromExcel("openAccount");
        String fetchedPageTitle = action.getTitle();
        logger.info("Expected page title: {}",expectedPageTitle);
        logger.info("Fetch page title: {}",fetchedPageTitle);
        if(expectedPageTitle.equalsIgnoreCase(fetchedPageTitle))
        {
            createAccountPageOpen = true;
        }
        return createAccountPageOpen;
    }
}
