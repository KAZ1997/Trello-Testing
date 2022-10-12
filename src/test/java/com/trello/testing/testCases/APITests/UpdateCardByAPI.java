package com.trello.testing.testCases.APITests;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.*;
import static io.restassured.RestAssured.given;

@Feature("Update Card")
public class UpdateCardByAPI extends BaseTest {

    /**
     * update the title and description of the provided card
     * @param idCard: the id of the card that will be updated.
     *              if there is no id card provided, a new id card will be created.
     * @param title: the new title of the card
     *             if there is no title card provided, a new title will be created.
     * @param description: the new description of the card
     *             if there is no description provided, a new description will be created.
     */

    @Parameters({"idCardToBeUpdated","cardTitleToBeUpdated", "cardDescriptionToBeUpdated"})
    @Description("Update card using trello API")
    @Test(description = "Update card")
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
         * print the response body
         */
        response.then().log().body();
    }
}
