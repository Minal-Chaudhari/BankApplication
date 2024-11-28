
package com.bankApp.base;

import com.bankApp.util.ActionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.testng.annotations.BeforeClass;

public class BaseClass {

    protected WebDriver driver; // encapsulation
    protected ActionUtils action;

    //for testdata sheet
    public static String testDataExcelPath = System.getProperty("user.dir")+ getProperty("testDataSheetPath");
    public final org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());


    @BeforeClass(groups = {"sanity", "smoke", "regression", "allTestSuite"})
    public void setUp() {

        //logger = LogManager.getLogger(this.getClass());
        logger.info("Setting up driver ...");

        String browser = getProperty("browser").toLowerCase();

        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
            driver = new ChromeDriver();
            logger.info("Chrome browser initialized.");
        } else if (browser.equals("edge")) {
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\msedgedriver.exe");
            driver = new EdgeDriver();
            logger.info("Edge browser initialized.");
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\geckodriver.exe");
            driver = new FirefoxDriver();
            logger.info("Firefox browser initialized.");
        } else {
            logger.error("Invalid browser specified in the properties file: {}", browser);
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        logger.info("Driver setup complete ...");

        action = new ActionUtils(driver);
    }



    @AfterClass(groups = {"sanity","smoke","regression","allTestSuite"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver tear down complete ...");
        }
    }



    //method will take key and return value data
    public static String getProperty(String key) {
        String configFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
        //logger.info(configFilePath);
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);

            //checking if the key exists in the properties file
            String propertyValue = properties.getProperty(key);
            if (propertyValue == null) {
                throw new RuntimeException("Property with key '" + key + "' not found in config.properties");
            }

            //trimming unwanted spaces
            return propertyValue.trim();
        } catch (IOException e) {
            throw new RuntimeException("Error reading config.properties file", e);
        }
    }


    //here screenshot method is present
    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;

    }


    //static block for timestamp
    static{
        //seetting up the log file with a timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String logFileName = "./log/BankApplicationTestExecution-" + timestamp + ".log";

        //dynamically update the log configuration
        LoggerContext context = (LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        //create a new file appender with the TIMESTAMPED log file
        FileAppender appender = FileAppender.newBuilder()
                .setName("FileLogger")
                .withFileName(logFileName)
                .withLayout(PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n").build())
                .build();

        appender.start();
        config.addAppender(appender);

        AppenderRef ref = AppenderRef.createAppenderRef("FileLogger", null, null);
        LoggerConfig loggerConfig = config.getRootLogger();
        loggerConfig.addAppender(appender, null, null);

        context.updateLoggers();
    }

}
