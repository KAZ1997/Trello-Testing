package com.trello.testing.testCases.CombinedTests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.BoardPage;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.*;

@Feature("Create Card")
public class CreateCardByAPI_VerifyByUI extends BaseTest {
    /**
     * test Case to create new card using API
     * a new card should be created inside the list that its id was passed
     * @param idList : the id of the list that the card will be created inside it
     */
    @Parameters("idList")
    @Description("Create card using trello API and verify its creation by selenium web driver")
    @Test(description = "Create card by API and verify it by UI")
    public void createNewCard(@Optional("")String idList){
        /**
         * if the id of the list was not passed, a new board with a new list will be generated automatically
         */
        if(idList==null || idList.trim().equalsIgnoreCase("")){
            String idBoard = idBoardGenerator();
            idList = idListGenerator(idBoard);
        }

        Response response = generateCard(idList);

        /**
         * check the existence of the card and the correctness of its title
         */
        String urlCard = response.then().extract().body().path("url");
        String nameCard = response.then().extract().body().path("name");

        BoardPage boardPage = new LoginPage(driver)
                .loginToBoardUrl(urlCard)
                .waitForPageToBeLoaded();

        String actualNameCard = boardPage.getCardTitle();
        Assert.assertEquals(actualNameCard, nameCard);

    }
}
