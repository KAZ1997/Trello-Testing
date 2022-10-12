package com.trello.testing.testCases.APITests;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@Slf4j
@Feature("Delete Board")
public class DeleteAllBoardsByAPI extends BaseTest {
    /**
     * delete all boards in the workplace
     * it gets all boards in the workplace. Then, it deleted each one.
     */
    @Description("Delete all boards using trello API")
    @Test(description = "Delete all boards")
    public void deleteAllBoards(){

        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("fields","url")
                .filter(new AllureRestAssured())
                .when()
                .get("members/me/boards")
                ;
        response.then().assertThat().statusCode(200);
        ArrayList<String> ids = response.then().extract().path("id");

        if (ids.size()>0){
            DeleteBoardByAPI deleter = new DeleteBoardByAPI();
            for (String id: ids){
                deleter.deleteBoard(id);
                log.info("Board with id: "+id+" has been deleted");
            }
        }else {
            log.info("No Boards Found!");
        }
    }
}
