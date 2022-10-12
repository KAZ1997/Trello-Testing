package com.trello.testing.testCases.APITests;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.*;


@Feature("Create List")
public class CreateListByAPI extends BaseTest {

    /**
     * Test the creation of List Using API
     * Expected result: a new list should be created inside board that its id is provided.
     * if there is no id board provided, a new board will be created and the new list will be created inside it.
     * @param idBoard: the id of the board the new list will be created inside it
     */
    @Parameters("idBoardToCreateList")
    @Description("Create new list using trello API")
    @Test(description = "Create new list")
    public void createNewListByAPI(@Optional("") String idBoard){
        if(idBoard==null || idBoard.trim().equalsIgnoreCase("")){
            idBoard = idBoardGenerator();
        }
        Response response = generateList(idBoard);
        response.then().log().body();
    }
}
