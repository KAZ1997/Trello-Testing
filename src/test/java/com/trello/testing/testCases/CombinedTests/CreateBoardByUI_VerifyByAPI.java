package com.trello.testing.testCases.CombinedTests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.BoardPage;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
@Slf4j
@Feature("Create Board")
public class CreateBoardByUI_VerifyByAPI extends BaseTest {
    /**
     * Test the creation of boards using UI
     * Expected result: a new board should be created
     */
    @Description("Create board using Selenium web driver and verify its creation by Trello API")
    @Test(description = "Create board by UI and verify it by API")
    public void createNewBoardByUI(){
        /**
         * * login and create a board using UI and wait until the board page to be loaded. If it is loaded successfully,
         * it means the test case succeeded because in case it fails, the board page will not be found!
         */
        BoardPage boardPage = new LoginPage(driver)
                .login()
                .waitForPageToBeLoaded()
                .createBoard().
                waitForPageToBeLoaded();

        /**
         * getting the board url to check by the API that
         * the board is created
         */
        String url = driver.getCurrentUrl();

        /**
         * getting the board Title to check by the API that
         * the board is created
         */
        String boardName = boardPage.getBoardTitle();
        /**
         * print the board url
         */
        log.info("Url of te new board: "+url);

        /**
         * getting all boards related to the user
         */
        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("fields","url", "name")
                .filter(new AllureRestAssured())
                .when()
                .get("members/me/boards")
                ;

        /**
         * print the body of the response
         */
        log.info("Response: Body");
        response.then().log().body();

        /**
         * check if the response is successful
         */
        response.then().assertThat().statusCode(200);

        ArrayList<String> urls = response.then().extract().path("url");
        int urlIndex = urls.indexOf(url);
        Assert.assertNotEquals(urlIndex,-1);

        ArrayList<String> titles = response.then().extract().path("name");
        int titleIndex = titles.indexOf(boardName);
        Assert.assertNotEquals(titleIndex,-1);

        Assert.assertEquals(urlIndex,titleIndex);
        log.info("The board has been successfully created and its index is: "+urlIndex);
    }
}
