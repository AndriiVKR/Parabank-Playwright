package com.example.tests;

import com.example.utils.TestUtil;
import com.microsoft.playwright.Page;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.AssertJUnit.assertTrue;

public class AccountOverviewTest extends BaseTest{


    @Epic("Account Overview Page displayed Tests")
    @Feature("Valid Login")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials")
    public void verifyAccountOverviewTest(String id, String username, String password) {
        Allure.step("User logged in");
        login(username, password);
        if (!accountOverviewPage.isAccountOverviewPageDisplayed()) {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-artifacts/screenshots/account-overview-fail.png")));
            Assert.fail("Account overview page is not displayed");
        }
        Assert.assertTrue(true); // Reached only if no error
    }

    @Epic("User should be able to click Open New Account Tests")
    @Feature("Valid Login")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials")
    public void verifyUserClickOpenNewAccountButtonTest(String id, String username, String password) {
        Allure.step("User logged in");
        login(username, password);
        Allure.step("Clicking open new account button");
        accountOverviewPage.clickOpenNewAccountButton();
        assertThat(page).hasTitle("ParaBank | Open Account");
    }
}
