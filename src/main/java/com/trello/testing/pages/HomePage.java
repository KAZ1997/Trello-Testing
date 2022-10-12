package com.trello.testing.pages;

import com.trello.testing.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.trello.testing.testData.TestDataGenerator.nameGenerator;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }
    private final By createButton = By.xpath("//button[@data-test-id='header-create-menu-button']");
    private final By createBoardOption = By.xpath("//button[@data-test-id='header-create-board-button']");
    private final By BoardTitleFiled = By.xpath("//input[@type='text']");
    private final By createBoardButton = By.xpath("//button[contains(text(),'Create')]");
    private final String boardTitle = "Test Board: "+nameGenerator();;
    public HomePage waitForPageToBeLoaded() {
        waitToBeClickable(createButton);
        return this;
    }

    public BoardPage createBoard(){
        click(createButton);
        click(createBoardOption);
        sendKeys(BoardTitleFiled, boardTitle);
        click(createBoardButton);
        return new BoardPage(driver);
    }
}
