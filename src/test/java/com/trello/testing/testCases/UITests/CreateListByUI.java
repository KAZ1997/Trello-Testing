package com.trello.testing.testCases.UITests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.BoardPage;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.urlBoardGenerator;

@Feature("Create List")
public class CreateListByUI extends BaseTest {

    /**
     * should create a new list in the board that its url is provided
     * @param urlBoard: url of the board that the new list will be created inside it
     *                if it is not provided, a new board will be created and the new list will be created inside it
     */
    @Parameters("urlBoardToCreateList")
    @Description("Create list using selenium web driver")
    @Test(description = "Create list")
    public void createNewList(@Optional("") String urlBoard) {
        /**
         * check if the parameters are provided or need to generate new one
         */

        if(urlBoard==null || urlBoard.trim().equalsIgnoreCase("")){
            urlBoard = urlBoardGenerator();
        }


        /**
         * login to the board page and count number of lists. Then, it creates a new list
         * Finally, it counts the number of lists again and make sure that it fouls to the old one +1
         */
        BoardPage boardPage = new LoginPage(driver)
                .loginToBoardUrl(urlBoard)
                .waitForPageToBeLoaded();


        int oldNumberOfLists = boardPage.getNumberOfLists();

        boardPage.createList();

        int newNumberOfLists = boardPage.getNumberOfLists();
        Assert.assertEquals(newNumberOfLists , oldNumberOfLists+1);

    }
}
