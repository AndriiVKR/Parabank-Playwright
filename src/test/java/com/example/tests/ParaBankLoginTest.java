
package com.example.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class ParaBankLoginTest extends BaseTest {

    @Epic("Login Tests")
    @Feature("Valid Login")
    @Story("User logs in with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "validCredentials", description = "Verify valid login")
    public void loginTestWithValidCredentials(String userId, String username, String password) {
        Allure.step("Navigating to login page");
        paraBankLoginPage.navigateToLoginPage();

        Allure.step("Logging in with username: " + username);
        paraBankLoginPage.login(username, password);

        Allure.step("Verifying welcome message");
        String welcomeMessage = paraBankLoginPage.getWelcomeMessage();

        if (!welcomeMessage.contains("Welcome")) {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-artifacts/screenshots/failure-" + userId + ".png")));
            Assert.fail("Login failed for user: " + username);
        }
    }

//    @Test(dataProvider = "validCredentials")
//    public void loginTestWithValidCredentials(String userId, String username, String password) {
//        paraBankLoginPage.navigateToLoginPage();
//        paraBankLoginPage.login(username, password);
//
//        String welcomeMessage = paraBankLoginPage.getWelcomeMessage();
//        if (!welcomeMessage.contains("Welcome")) {
//            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-artifacts/screenshots/failure-" + userId + ".png")));
//            Assert.fail("Login failed for user: " + username + " — 'Welcome' message not found.");
//        }
//    }
@Epic("User should be able to see error message when entered invalid credentials Tests")
@Feature("Valid Login")
@Story("User logs in with correct credentials")
@Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "invalidCredentials")
    public void loginTestWithInvalidUsername(String id, String username, String password) {
        Allure.step("Navigating to login page");
        paraBankLoginPage.navigateToLoginPage();
        Allure.step("Logging in with username: " + username);
        paraBankLoginPage.login(username, password);
        Assert.assertTrue(paraBankLoginPage.isLoginErrorMassageDisplayed(), "Please enter a username and password.");
    }

}
