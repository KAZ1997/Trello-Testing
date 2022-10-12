package com.trello.testing.pages;

import com.trello.testing.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.trello.testing.testData.TestDataGenerator.nameGenerator;

public class BoardPage extends BasePage {
    public BoardPage(WebDriver driver) {super(driver);}

    private final By boardViewsButton = By.xpath("//button[@title='Board views']");
    private final By lists = By.xpath("(//div[@class='js-list list-wrapper'])");
    private final By listTitleInput = By.xpath("//input[@placeholder='Enter list title…']");
    private final By createListButton = By.xpath("//span[@class='placeholder']");
    private final By addButton = By.xpath("//input[@value='Add list']");
    private final By cardTitleField =By.xpath("//textarea[@class='mod-card-back-title js-card-detail-title-input']");
    private final By cardTitleFieldEditMode = By.xpath("//textarea[@class='mod-card-back-title js-card-detail-title-input is-editing']");
    private final By cardDescriptionField = By.xpath("//a[contains(text(),'Add a more detailed description…')]");
    private final By cardDescriptionFieldEditMode = By.xpath("//textarea[@placeholder='Add a more detailed description…']");
    private final By cardDescriptionSaveButton = By.cssSelector("input[class='nch-button nch-button--primary confirm mod-submit-edit js-save-edit']");
    private final By cardDescription = By.cssSelector("p[dir='auto']");
    private final By boardTitle = By.cssSelector(".js-board-editing-target.board-header-btn-text");
    public BoardPage waitForPageToBeLoaded() {
        waitToBeClickable(boardViewsButton);
        return this;
    }

    public int getNumberOfLists(){
        return countNumberOfElements(lists);
    }
    public void createList(){
        click(createListButton);
        String listName = "Test List: "+ nameGenerator();
        sendKeys(listTitleInput,listName);
        click(addButton);
    }

    public BoardPage updateCardTitle(String title){
        click(cardTitleField);
        clear(cardTitleFieldEditMode);
        driver.findElement(cardTitleFieldEditMode).sendKeys(title);
        return this;
    }

    public BoardPage updateCardDescription(String description){
        click(cardDescriptionField);
        driver.findElement(cardDescriptionFieldEditMode).sendKeys(description);
        click(cardDescriptionSaveButton);
        return this;
    }

    public String getCardTitle(){
        driver.findElement(cardTitleField).click();
        return getValue(cardTitleFieldEditMode);
    }
    public String getCardDescription(){
        return getText(cardDescription);
    }
    public String getBoardTitle(){
        return getText(boardTitle);
    }

}
