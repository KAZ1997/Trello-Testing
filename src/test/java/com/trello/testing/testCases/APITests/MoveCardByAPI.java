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

@Feature("Move Card")
public class MoveCardByAPI extends BaseTest {
    /**
     * move a card to a different list.
     * @param idCardToBeMoved: id of the card that will be moved
     * @param newIdListForCard: the id of the list that the card will move to.
     */
    @Parameters({"idCardToBeMoved","newIdListForCard"})
    @Description("Move card to a different list using trello API")
    @Test(description = "Move card to a different list")
    public void moveCard(@Optional("") String idCardToBeMoved, @Optional("")  String newIdListForCard){
        String idCard=idCardToBeMoved;
        String idBoard ="";

        if(newIdListForCard==null || newIdListForCard.trim().equalsIgnoreCase("")){
            idBoard= idBoardGenerator();
            newIdListForCard= idListGenerator(idBoard);
        }else {
            idBoard = given()
                    .queryParam("key", apiKey)
                    .queryParam("token", apiToken)
                    .filter(new AllureRestAssured())
                    .when()
                    .get("lists/"+newIdListForCard).then().extract().body().path("idBoard");
        }

        if(idCard==null || idCard.trim().equalsIgnoreCase("")){
            String idList = idListGenerator(idBoard);
            idCard = idCardGenerator(idList);
        }

        JSONObject requestParams = new JSONObject();
        requestParams.put("idList", newIdListForCard);
        requestParams.put("idBoard", idBoard);
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .put("cards/" + idCard)
                ;
        response.then().assertThat().statusCode(200);
        response.then().log().body();
    }
}
