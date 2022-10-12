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

import static com.trello.testing.testData.TestDataGenerator.idBoardGenerator;
import static io.restassured.RestAssured.given;


@Feature("Delete Board")
public class DeleteBoardByAPI extends BaseTest {
    /**
     * delete the board that its id is provided
     * if there is no id provided, a new board will be created and deleted
     * @param idBoard: the id of the board that will be deleted
     */
    @Parameters("idBoardToBeDeleted")
    @Description("delete a board using trello API")
    @Test(description = "Delete board")
    public void deleteBoard(@Optional("")String idBoard){

        if(idBoard==null || idBoard.trim().equalsIgnoreCase("")){
            idBoard = idBoardGenerator();
        }

        /**
         * add API key and API token to json object
         */
        JSONObject requestParams = new JSONObject();
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        /**
         * delete request
         */
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .delete("boards/"+idBoard)
                ;
        response.then().assertThat().statusCode(200);
    }
}
