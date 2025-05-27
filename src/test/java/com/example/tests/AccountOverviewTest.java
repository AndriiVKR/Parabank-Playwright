package com.example.tests;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

public class AccountOverviewTest extends BaseTest{

    @Test(dataProvider = "credentials")
    public void verifyAccountOverviewTest(String id, String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
        assertTrue(accountOverviewPage.isAccountOverviewPageDisplayed());
    }
}
