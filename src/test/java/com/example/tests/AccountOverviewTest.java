package com.example.tests;

import com.example.pages.AccountOverviewPage;
import com.example.pages.ParaBankLoginPage;
import org.testng.annotations.Test;



import static org.testng.AssertJUnit.assertTrue;

public class AccountOverviewTest extends BaseTest{

    @Test(dataProvider = "credentials")
    public void verifyAccountOverviewTest(String id, String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
        AccountOverviewPage accountOverviewPage = new AccountOverviewPage(page);
        assertTrue(accountOverviewPage.isAccountOverviewPageDisplayed());
    }
}
