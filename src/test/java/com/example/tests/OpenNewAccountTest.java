package com.example.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OpenNewAccountTest extends BaseTest{

    @Epic("User should be able to click Open New Account Tests")
    @Feature("Valid Login")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials")
    public void verifyUserClickOpenNewAccountButtonTest(String id, String username, String password) {
        navigateToOpenNewAccountPage(username, password);
        assertThat(page).hasTitle("ParaBank | Open Account");
    }

    @Epic("User should be able to select Saving Account Tests")
    @Feature("Open New Account")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials")
    public void verifyUserCanSelectSavingsAccount(String id, String username, String password) {
        Allure.step("Navigating to open new account page");
        navigateToOpenNewAccountPage(username, password);
        Allure.step("Selecting savings account");
        openNewAccountPage.selectSavingsAccount();
        Assert.assertEquals(openNewAccountPage.getSelectedAccountType(), "1");
    }

    @Epic("User should be able to select Checking Account Tests")
    @Feature("Valid Login")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials")
    public void verifyUserCanSelectCheckingAccount(String id, String username, String password) {
        Allure.step("Navigating to open new account page");
        navigateToOpenNewAccountPage(username, password);
        Allure.step("Selecting checking account");
        openNewAccountPage.selectCheckingAccount();
        Assert.assertEquals(openNewAccountPage.getSelectedAccountType(), "0");
    }

}
