package com.trello.testing.pages;

import com.trello.testing.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By emailFiled = By.id("user");
    private final By passwordFiled = By.id("password");
    private final By firstLoginButton = By.id("login");
    private final By loginButton = By.id("login-submit");
    private final By loginToUrlButton = By.xpath("//a[normalize-space()='Log in']");

    private final String email = properties.getString("email");
    private final String password = properties.getString("password");
    private final String loginUrl = "https://trello.com/login";

    public HomePage login(){
        load(loginUrl);
        sendKeys(emailFiled,email);
        click(firstLoginButton);
        waitToBeClickable(loginButton);
        sendKeys(passwordFiled, password);
        click(loginButton);
        return new HomePage(this.driver);
    }

    public BoardPage loginToBoardUrl(String url){
        load(url);
        click(loginToUrlButton);
        sendKeys(emailFiled,email);
        click(firstLoginButton);
        waitToBeClickable(loginButton);
        sendKeys(passwordFiled, password);
        click(loginButton);
        return new BoardPage(this.driver);
    }
}
