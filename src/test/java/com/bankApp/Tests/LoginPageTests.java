package com.bankApp.Tests;


import com.bankApp.base.BaseClass;
import com.bankApp.dataProvider.TestData;
import com.bankApp.pages.loginPage.LoginPage;
import com.bankApp.util.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class LoginPageTests extends BaseClass {

    LoginPage login ;

    @BeforeClass(groups = {"sanity","smoke","regression","allTestSuite"})
    public void initializeLoginPage() {
        if (driver == null) {
            setUp();
        }
        login = new LoginPage(driver);
        logger.info("Login Page is Initialised");

        driver.navigate().to(getProperty("loginUrl"));
        Assert.assertTrue(login.isLoginPageOpened(),"Login Page is not opened");
        logger.info("Login Page is opened");
    }


    @Test(priority = 1,description = "LGN_001: Validate valid login with UserName", groups = {"smoke","regression","sanity","allTestSuite"})
    public void validLoginWithUserName(){
        login.validSignInWithUserName();
        Assert.assertTrue(login.isLoginSuccessful(),"User is not logged in");
        login.logOut();
        Assert.assertTrue(login.isLoginPageOpened(),"User is not logged Out");
    }

    @Test(priority = 1,description = "LGN_002: Validate valid login with Email", groups = {"smoke","regression","sanity","allTestSuite"})
    public void validLoginWithEmail(){
        login.validSignInWithEmail();
        Assert.assertTrue(login.isLoginSuccessful(),"User is not logged in");
        login.logOut();
        Assert.assertTrue(login.isLoginPageOpened(),"User is not logged Out");
    }

    @Test(priority = 1,description = "LGN_003: Validate invalid login", groups = {"smoke","regression","sanity","allTestSuite"},dataProviderClass = TestData.class, dataProvider = "fetchInvalidLoginTestData")
    public void invalidLogin(Map<String, String> testData){

        String userNameOrEmail = testData.get("Username/Email");
        String password = testData.get("Password");
        logger.info("fetched username/email: {}",userNameOrEmail);
        logger.info("fetched password: {}",password);
        login.invalidSignIn(userNameOrEmail,password);
        login.verifyAlertMessage();
        login.clickAlertOK();
        login.clearUserNameAndPasswordField();
        //Assert.assertFalse(login.isLoginSuccessful(),"User is logged in");

    }

    @Test(priority = 1,description = "LGN_001: Validate invalid login with blank username/emailID field", groups = {"smoke","regression","sanity","allTestSuite"})
    public void verifyLoginWithBlankUserNameOrEmailID(){

    }

    @Test(priority = 1,description = "LGN_001: Validate invalid login with blank password field", groups = {"smoke","regression","sanity","allTestSuite"})
    public void verifyLoginWithBlankPassword(){

    }

    @Test
    public void testDataBaseConnectivity() {
        String db2Url = BaseClass.getProperty("dbUrlBankAccount");

        String userName = BaseClass.getProperty("employeeName");
        // Query to validate in Database 1
        String query1 = "SELECT * FROM account WHERE user_name = " + "'" + userName + "'";

        // Use DatabaseUtils to check if the record exists
        boolean isUserInDb1 = DatabaseUtils.recordExists(db2Url, query1);

        Assert.assertTrue(isUserInDb1, "User not found in Database 1.");
    }


}
