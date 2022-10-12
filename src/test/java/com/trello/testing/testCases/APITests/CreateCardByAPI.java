package com.trello.testing.testCases.APITests;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.*;
@Feature("Create Card")
public class CreateCardByAPI extends BaseTest {

    /**
     * test Case to create new card using API
     * a new card should be created inside the list that its id was passed
     * @param idList : the id of the list that the card will be created inside it
     */
    @Parameters("idList")
    @Description("Create new card using trello API")
    @Test(description = "Create new card")
    public void createNewCard(@Optional("")String idList){
        /**
         * if the id of the list was not passed, a new board with a new list will be generated automatically
         */
        if(idList==null || idList.trim().equalsIgnoreCase("")){
            String idBoard = idBoardGenerator();
            idList = idListGenerator(idBoard);
        }

        Response response = generateCard(idList);
        response.then().assertThat().statusCode(200);
    }
}
