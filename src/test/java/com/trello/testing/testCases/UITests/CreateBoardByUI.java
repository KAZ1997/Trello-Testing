package com.trello.testing.testCases.UITests;

import com.trello.testing.base.BaseTest;
import com.trello.testing.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
@Feature("Create Board")
public class CreateBoardByUI extends BaseTest {
    /**
     * Test the creation of boards using UI
     * Expected result: a new board should be created
     */
    @Description("Create board using selenium web driver")
    @Test(description = "Create board")
    public void createNewBoardByUI(){
        /**
         * login and create a board using UI and wait until the board page to be loaded. If it is loaded successfully,
         * it means the test case succeeded because in case it fails, the board page will not be found!
         */
        new LoginPage(driver)
                .login()
                .waitForPageToBeLoaded()
                .createBoard().
                waitForPageToBeLoaded();
    }
}
