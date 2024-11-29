package com.bankApp.Tests;

import com.bankApp.base.BaseClass;
import com.bankApp.pages.createAccountPage.CreateAccountPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class CreateAccountPageTests extends BaseClass{

    CreateAccountPage createAccount ;

    @BeforeClass(groups = {"sanity","smoke","regression","allTestSuite"})
    public void initializeLoginPage() {
        if (driver == null) {
            setUp();
        }
        createAccount = new CreateAccountPage(driver);
        logger.info("Create Account Page is Initialised");

        driver.navigate().to(getProperty("createAccountUrl"));
        Assert.assertTrue(createAccount.isCreateAccountPageOpened(),"Create Account Page is not opened");
        logger.info("Create Account Page is opened");
    }


    @Test
    public void createNewAccount(Map<String, String> testData){

        //initializing all excel values for dynamic fetching
        String FirstName = testData.get("FirstName");
        String LastName = testData.get("LastName");
        String Email = testData.get("Email");
        String Gender = testData.get("Gender");
        String PhoneNumber = testData.get("PhoneNumber");
        String AlternatePhoneNumber = testData.get("AlternatePhoneNumber");
        String Address = testData.get("Address");
        String State = testData.get("State");
        String City = testData.get("City");
        String Zip = testData.get("Zip");
        String AccountType = testData.get("AccountType");

        logger.info("Values are fetched");
        createAccount.fillCreateAccountForm(FirstName,LastName,Email,Gender,PhoneNumber,AlternatePhoneNumber,Address,State,City,Zip,AccountType);

        logger.info("Addition of values complete");
        createAccount.addProfilePicAndID();
        createAccount.submitForm();



    }
}
