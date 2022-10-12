package com.trello.testing.testCases.APITests;

import com.trello.testing.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.trello.testing.testData.TestDataGenerator.generateBoard;

@Slf4j
@Feature("Create Board")
public class CreateBoardByAPI extends BaseTest {

    /**
     * Test the creation of boards Using API
     * Expected result: a new board should be created
     * Note: all assertions are in the generateBard method
     */
    @Description("Create new board using trello API")
    @Test(description = "Create new board")
    public void createNewBoardByAPI(){
        generateBoard();
    }
}
