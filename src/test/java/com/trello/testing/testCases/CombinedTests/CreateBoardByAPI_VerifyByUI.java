package com.trello.testing.testCases.CombinedTests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.BoardPage;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.generateBoard;

@Slf4j
@Feature("Create Board")
public class CreateBoardByAPI_VerifyByUI extends BaseTest {
    /**
     * Test the creation of boards Using API
     * Expected result: a new board should be created
     * Note: all assertions are in the generateBard method
     */
    @Description("Create board using trello API and verify its creation by selenium web driver")
    @Test(description = "Create board by API and verify it by UI")
    public void createNewBoardByAPI(){
        Response response =generateBoard();

        String urlBoard= response.then().extract().body().path("url");
        String boardName = response.then().extract().body().path("name");
        BoardPage boardPage = new LoginPage(driver)
                .loginToBoardUrl(urlBoard)
                .waitForPageToBeLoaded();

        String actualTitle = boardPage.getBoardTitle();
        Assert.assertEquals(actualTitle,boardName);
    }
}
