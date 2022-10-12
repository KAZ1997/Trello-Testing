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

import static com.trello.testing.testData.TestDataGenerator.*;

@Feature("Update Card")
public class UpdateCardByUI extends BaseTest {

    /**
     * update the title and description of the provided card
     * @param urlCard: the url of the card that will be updated.
     *              if there is no url card provided, a new url card will be created.
     * @param title: the new title of the card
     *             if there is no title card provided, a new title will be created.
     * @param description: the new description of the card
     *             if there is no description provided, a new description will be created.
     */
    @Parameters({"urlCardToBeUpdated","cardTitleToBeUpdated", "cardDescriptionToBeUpdated"})
    @Description("update card using selenium web driver")
    @Test(description = "update card by UI")
    public void update(@Optional("") String urlCard, @Optional("")  String title, @Optional("")  String description) {

        /**
         * check if the parameters are provided or need to generate new one
         */
        if(urlCard==null || urlCard.trim().equalsIgnoreCase("")){
            urlCard = urlCardGenerator();
        }
        if(title==null || title.trim().equalsIgnoreCase("")){
            title = "Updated Title" + nameGenerator();
        }
        if(description==null || description.trim().equalsIgnoreCase("")){
            description = "Updated Description" + nameGenerator();
        }

        /**
         * open the card page and update the title and description fileds
         */
        BoardPage boardPage = new LoginPage(driver)
                .loginToBoardUrl(urlCard)
                .waitForPageToBeLoaded().updateCardTitle(title)
                .updateCardDescription(description);

        /**
         * test actual data and expected data
         */
        Assert.assertEquals(boardPage.getCardTitle(),title);
        Assert.assertEquals(boardPage.getCardDescription(),description);
    }
}
