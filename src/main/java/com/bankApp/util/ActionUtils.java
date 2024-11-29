package com.bankApp.util;


import com.bankApp.base.BaseClass;
import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ActionUtils {


    private WebDriver driver;
    private WaitUtils waitUtils;
    private Actions actions;
    private Properties properties;

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ActionUtils.class);

    //assign driver to call from test class
    public ActionUtils(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.actions = new Actions(driver);
        //this.logger = logger;

    }

    //=====================================common methods============================================

    //method will get xpath and return webelement element
    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    //get and check the url linked with hyperlink (actual and expected)
    public boolean checkHyperlink(By linkLocator, String expectedUrl) {
        try {

            waitUtils.waitForElementToBeVisible(linkLocator,10);
            WebElement link = driver.findElement(linkLocator);

            //get attribute will fetch "href(link)"
            String actualUrl = link.getAttribute("href");

            logger.info("Fetched URL: {}", actualUrl);

            //checking if actual url is fetched
            assert actualUrl != null;
            return actualUrl.equals(expectedUrl);

        } catch (NoSuchElementException e) {
            logger.info("Element not found: {}", linkLocator);
            return false;
        }
    }

    //navigate to URL
    public void navigateToURL(String URL){
        logger.info("Navigating to URL: {}",URL);
        driver.get(URL);

    }

    //waits until locator is visible
    public void waitUntilFieldIsVisible(By locator){
        logger.info("Waiting until Element is visible...");
        waitUtils.waitForElementToBeVisible(locator,20);
    }

    //waits until locator is clickable
    public void waitUntilElementIsClickable(By locator){
        logger.info("Waiting until Element is clickable...");
        waitUtils.waitForElementToBeClickable(locator,20);
    }

    //gets text
    public String getText(By locator){
        WebElement element = driver.findElement(locator);
        logger.info("Fetching text...");
        return element.getText();
    }

    //gets title of page
    public String getTitle() {
        logger.info("Fetching title of page...");
        return driver.getTitle();
    }

    //checks if any button is displayed
    public boolean isButtonDisplayed(By buttonXpath) {
        WebElement button = driver.findElement(buttonXpath);
        logger.info("Checking button display...");
        return button.isDisplayed();
    }

    //click button
    public void clickButton(By locator) {
        logger.info("Clicking button");
        driver.findElement(locator).click();
    }

    //method will switch to new open tab
    public void switchToNewTab() {

        //current window
        String mainWindowHandle = driver.getWindowHandle();
        //all windows
        Set<String> allWindowHandles = driver.getWindowHandles();

        //switching to the new tab opened by user
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle); //here new tab is switched
                logger.info("Switched to new window");
                break;
            }
        }
        logger.info("Waiting for element to be visible...");
        //waitUtils.waitForElementToBeVisible(textToWaitBeforeTitleFetch,10);
    }

    //this method will switch bask to main flipkart tab
    public void switchBackToMainTab() {
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        logger.info("Switched back to main frame");
    }

    //locate element and insert value
    public void insertValue(String value) {
        logger.info("Inserted value: {}", value);
        driver.switchTo().activeElement().sendKeys(value);
    }

    //for valid emailID and pass (since they are sensitive)
    public void insertSensitiveValue(String value)
    {
        logger.info("Inserted sensitive value");
        driver.switchTo().activeElement().sendKeys(value);
    }

    //method will click on field and send values
    public void clickAndSendValue(String value, By locator){
        WebElement element = driver.findElement(locator);
        element.click();
        element.sendKeys(value);
    }

    //get popup test
    public String getPopUpText(By locator) {
        String popUpText = null;
        waitUtils.waitForElementToBeVisible(locator, 10);

        try {
            WebElement popUp = driver.findElement(locator);
            popUpText = popUp.getText();
            logger.info("Fetched PopUp text: {}",popUpText);
        } catch (NoSuchElementException e) {
            logger.error("Popup element not found: {}" , e.getMessage());
        } catch (StaleElementReferenceException e) {
            logger.error("Popup element is stale: {}" , e.getMessage());
        }

        return popUpText;
    }

    //method will hover over element
    public void hoverOverElement(By locator) {
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element).perform();
        logger.info("Hovering over element...");
    }

    //method will fetch all the values in a list using locator (return list)
    public List<String> fetchAllTextValuesUsingLocator(By locator) {
        /*
        create list to store webelements (findelementS)
        create list to store text
        use for each to add all text from weblement list to text list
        return text list
         */

        List<WebElement> elements = driver.findElements(locator);
        List<String> allFetchedValues = new ArrayList<>();

        logger.info("Fetching all text values");
        for (WebElement element : elements) {
            allFetchedValues.add(element.getText().trim());
        }


        return allFetchedValues;
    }

    //method to load property file
    public String getPropertyValue(String key, String fileName) {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream input = new FileInputStream(System.getProperty("user.dir")+"\\propertyFiles\\"+fileName+".properties");
                properties.load(input);
                logger.info("Loaded property file: {}",fileName);
                input.close();
            } catch (IOException e) {
                logger.error("Property file loading fails: {}",fileName);
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

    //method will click enter from keyboard ... driver.submit() cab be used
    public void clickEnter(By locator){
        WebElement element = driver.findElement(locator);
        element.sendKeys(Keys.ENTER);
        logger.info("Enter button is clicked");
    }

    //method will wait for page to load .. max wait time is 20
    public void waitForPageToLoad(){
        waitUtils.waitForPageToLoad(20);
        logger.info("Waiting for page to load");
    }

    //method to check if radio button is selected
    public boolean isRadioButtonSelected(By locator){
        WebElement radioElement = driver.findElement(locator);
        logger.info("Checking if radio button is selected");
        return radioElement.isSelected();
    }

    //method to select radio button
    public void selectRadioButton(By locator){
        WebElement radioElement = driver.findElement(locator);
        if (!radioElement.isSelected()) {
            radioElement.click();
        }
        logger.info("Radio button is selected");
    }

    //scroll until view
    public void scrollIntoView(By locator){
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickUsingJS(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    //method will print all items of list
    public void printListItems(List<String> items) {
        if (items == null || items.isEmpty()) {
            logger.warn("The list is empty or null.");
            return;
        }
        for (String item : items) {
            logger.info(item);
        }
    }

    //method will check if button is enabled
    public boolean isButtonEnabled(By locator){
        WebElement element = driver.findElement(locator);
        return element.isEnabled();
    }

    //method will get the expected title from given sheet "PageTitle".. this method is only for page titles .. try to use same for constants
    public String getExpectedPageTitleFromExcel(String pageNameKey) {

        ExcelUtils excelUtils = new ExcelUtils(BaseClass.testDataExcelPath, "PageTitle");
        int rowCount = excelUtils.getRowCount();

        //iterate to find matching page name
        for (int i = 1; i < rowCount; i++) { // Start from 1 to skip header
            String pageName = excelUtils.getCellData(i, 0); //column 0: PageName
            if (pageName != null && pageName.equalsIgnoreCase(pageNameKey)) {
                return excelUtils.getCellData(i, 1); //column 1: PageTitle
            }
        }
        //closing workbook here
        excelUtils.closeWorkbook();
        throw new RuntimeException("PageName '" + pageNameKey + "' not found in Excel sheet.");
    }

    //method will clear field
    public void clearField(By locator){
        WebElement fieldName = driver.findElement(locator);
        fieldName.clear();
    }

    //method will fetch constant values
    public String getConstantFromExcel(String pageNameKey) {

        ExcelUtils excelUtils = new ExcelUtils(BaseClass.testDataExcelPath, "Constants");
        int rowCount = excelUtils.getRowCount();

        //iterate to find matching page name
        for (int i = 1; i < rowCount; i++) { // Start from 1 to skip header
            String pageName = excelUtils.getCellData(i, 0); //column 0: PageName
            if (pageName != null && pageName.equalsIgnoreCase(pageNameKey)) {
                return excelUtils.getCellData(i, 1); //column 1: PageTitle
            }
        }
        //closing workbook here
        excelUtils.closeWorkbook();
        throw new RuntimeException("PageName '" + pageNameKey + "' not found in Excel sheet.");
    }



}
