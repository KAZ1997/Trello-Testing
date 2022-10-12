package com.trello.testing.base;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    final protected Wait<WebDriver> wait;
    static final protected Config properties = ConfigFactory.load();


    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).
                pollingEvery(Duration.ofSeconds(1)).withMessage("Time out as the condition is not met");
    }

    public void load(String endPoint){
        driver.get(endPoint);
    }

    public void click(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void waitToBeClickable(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void sendKeys(By element, String value){
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(value);
    }
    protected int countNumberOfElements(By elements) {
        return driver.findElements(elements).size();
    }
    public void clear(By element){
        driver.findElement(element).clear();
    }
    public String getValue(By element){
        return driver.findElement(element).getAttribute("value");
    }
    public String getText(By element){
        return driver.findElement(element).getText();
    }
}
