package com.trello.testing.testCases.CombinedTests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.BoardPage;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.*;
import static com.trello.testing.testData.TestDataGenerator.nameGenerator;
import static io.restassured.RestAssured.given;

@Feature("Update Card")
public class UpdateCardByAPI_VerifyByUI extends BaseTest {

    /**
     * update the title and description of the provided card By API
     * After that, verify the updates from the UI
     * @param idCard: the id of the card that will be updated.
     *              if there is no id card provided, a new id card will be created.
     * @param title: the new title of the card
     *             if there is no title card provided, a new title will be created.
     * @param description: the new description of the card
     *             if there is no description provided, a new description will be created.
     */

    @Parameters({"idCardToBeUpdated","cardTitleToBeUpdated", "cardDescriptionToBeUpdated"})
    @Description("update card using trello API and verify its updating by selenium web driver")
    @Test(description = "Create card by UI and verify it by API")
    public void updateCard(@Optional("") String idCard, @Optional("")  String title, @Optional("")  String description){

        /**
         * check if the parameters are provided or need to generate new one
         */
        if(idCard==null || idCard.trim().equalsIgnoreCase("")){
            String idBoard = idBoardGenerator();
            String idList = idListGenerator(idBoard);
            idCard = idCardGenerator(idList);
        }
        if(title==null || title.trim().equalsIgnoreCase("")){
            title = "Updated Title" + nameGenerator();
        }
        if(description==null || description.trim().equalsIgnoreCase("")){
            description = "Updated Description" + nameGenerator();
        }


        /**
         * add parameters to the jason object
         */
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", title);
        requestParams.put("desc", description);
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        /**
         * request the update
         */
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .put("cards/" + idCard)
                ;

        /**
         * test actual API data and expected data
         */
        response.then().assertThat().statusCode(200);

        String actualTitle = response.then().extract().body().path("name");
        Assert.assertEquals(actualTitle, title);

        String actualDescription = response.then().extract().body().path("desc");
        Assert.assertEquals(actualDescription, description);

        /**
         * test actual UI data and expected data
         */
        String urlCard = response.then().extract().body().path("url");
        BoardPage boardPage = new LoginPage(driver)
                .loginToBoardUrl(urlCard)
                .waitForPageToBeLoaded();
        String actualUiTitle = boardPage.getCardTitle();
        Assert.assertEquals(actualUiTitle, title);

        String actualUiDescription = boardPage.getCardDescription();
        Assert.assertEquals(actualUiDescription, description);

        /**
         * print the response body
         */
        response.then().log().body();
    }
}
