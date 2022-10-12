package com.trello.testing.testData;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

@Slf4j
public class TestDataGenerator extends BaseTest {
    /**
     * generate new name consist of the current date and time
     * @return the generated name
     */
    @Step
    public static String nameGenerator(){
        String pattern = "yyyy.MM.dd.HH.mm.ss";
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * create a new board and return its id
     * @return id of the generated board
     */
    @Step
    public static String idBoardGenerator(){
        Response response = generateBoard();
        String idBoard = response.then().extract().body().path("id");
        log.info("Generated idBoard: "+idBoard);
        return idBoard;
    }

    /**
     * create a new board
     * @return response: the response of board creation request
     */
    @Step
    public static Response generateBoard(){
        String boardName = "Test Board: "+nameGenerator();

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", boardName);
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .post("boards/")
                ;
        response.then().assertThat().statusCode(200);
        response.then().log().body();
        String actualName = response.then().extract().body().path("name");
        Assert.assertEquals(actualName, boardName);
        return response;
    }

    /**
     * create a new list and return its id
     * @param idBoard: the id of the board that the new list will be created inside it
     * @return id of the generated list
     */
    @Step
    public static String idListGenerator(String idBoard){
        Response response = generateList(idBoard);
        String idList = response.then().extract().body().path("id");
        log.info("Generated idList: "+idList);
        return idList;
    }

    /**
     * create a new list
     * @param idBoard: the id of the board that the new list will be created inside it
     * @return response: the response of the list creation request
     */
    @Step
    public static Response generateList(String idBoard){
        String listName = "Test List: "+nameGenerator();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", listName);
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .post("boards/"+idBoard+"/lists")
                ;

        response.then().assertThat().statusCode(200);

        String actualListName = response.then().extract().body().path("name");
        Assert.assertEquals(actualListName, listName);

        String actualBoardId = response.then().extract().body().path("idBoard");
        Assert.assertEquals(actualBoardId, idBoard);

        return response;
    }


    /**
     * create a new card and return it id
     * @param idList: the id of the list that the new card will be created inside it
     * @return idCard: the id of the generated card
     */
    @Step
    public static String idCardGenerator(String idList){
        Response response = generateCard(idList);
        String idCard = response.then().extract().body().path("id");
        log.info("Generated idCard: "+idCard);
        return idCard;
    }

    /**
     * create a new card and return it response
     * @param idList: the id of the list that the new card will be created inside it
     * @return response of the card creation request
     */
    @Step
    public static Response generateCard(String idList){
        String cardName = "Test Card: "+nameGenerator();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", cardName);
        requestParams.put("idList", idList);
        requestParams.put("key", apiKey);
        requestParams.put("token", apiToken);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .filter(new AllureRestAssured())
                .when()
                .post("cards")
                ;

        response.then().assertThat().statusCode(200);

        String actualCardName = response.then().extract().body().path("name");
        Assert.assertEquals(actualCardName, cardName);

        String actualListId = response.then().extract().body().path("idList");
        Assert.assertEquals(actualListId, idList);

        return response;
    }

    /**
     * create a new board and return it url
     * @return urlBoard: the url of the created board
     */
    @Step
    public static String urlBoardGenerator(){
        Response response = generateBoard();
        String urlBoard = response.then().extract().body().path("url");
        log.info("Generated urlBoard: "+urlBoard);
        return urlBoard;
    }

    /**
     * create a new card inside a new list and board and return it url
     * @return urlCard: the url of the created card
     */
    @Step
    public static String urlCardGenerator(){
        String idBoard = idBoardGenerator();
        String idList = idListGenerator(idBoard);
        Response response = generateCard(idList);
        String urlCard = response.then().extract().body().path("url");
        log.info("Generated urlCard: "+urlCard);
        return urlCard;
    }
}
