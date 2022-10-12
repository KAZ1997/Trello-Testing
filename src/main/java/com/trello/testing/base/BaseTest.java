package com.trello.testing.base;

import com.trello.testing.factory.DriverManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;

import java.io.File;
import java.io.IOException;


public class BaseTest {
    static final protected Config properties = ConfigFactory.load();
    static final protected String apiKey = properties.getString("apiKey");
    static final protected String apiToken = properties.getString("apiToken");

    protected WebDriver driver;
    final String BASE_URI = "https://api.trello.com/1/";

    @BeforeClass
    public void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = BASE_URI;
    }

    @BeforeMethod(groups = { "uiTests"})
    public void startDriver(){
        driver = new DriverManager().initializeDriver();
    }


    @AfterMethod(groups = { "uiTests"})
    public void quiteDriver(@Optional ITestResult result) throws IOException {

        if(result.getStatus() == ITestResult.FAILURE){
            File destFile = new File("scr" + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");
            takeScreenshot(destFile);
        }

        driver.quit();
    }

    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) this.driver;
        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, destFile);
    }
}
