//package com.example.tests;
//
//import com.example.pages.ParaBankLoginPage;
//import com.example.utils.BrowserSetup;
//import com.example.utils.CredentialsReader;
//import com.microsoft.playwright.BrowserContext;
//import com.microsoft.playwright.Page;
//import com.microsoft.playwright.Tracing;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Map;
//
//import static org.testng.Assert.assertTrue;
//
//public class ParaBankLoginTest {
//    BrowserContext context;
//    Page page;
//    ParaBankLoginPage paraBankLoginPage;
//
//    @BeforeMethod
//    public void setUp() throws IOException {
//        context = BrowserSetup.getBrowserContext();
//        page = context.newPage();
//        paraBankLoginPage = new ParaBankLoginPage(page);
//    }
//
//    @DataProvider(name = "credentials")
//    public Object[][] getCredentials() throws IOException {
//        Map<String, CredentialsReader.UserCredentials> credentialsMap = CredentialsReader.loadLoginCredentials();
//        return new Object[][]{
//                {"user1", credentialsMap.get("user1").username(), credentialsMap.get("user1").password()},
////                {"user2", credentialsMap.get("user2").username(), credentialsMap.get("user2").password()}
//        };
//    }
//
//    @Test(dataProvider = "credentials")
//    public void testLogin(String userId, String username, String password) {
//        paraBankLoginPage.navigateToLoginPage();
//        paraBankLoginPage.login(username, password);
//        String welcomeMessage = paraBankLoginPage.getWelcomeMessage();
//        assertTrue(welcomeMessage.contains("Welcome"), "Login failed for " + userId + "!");
//        if (!welcomeMessage.contains("Welcome")) {
//            page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("failure-" + userId + ".png")));
//        }
//    }
//
////    @Test
////    public void testRegisterLink() {
////        paraBankLoginPage.navigateToLoginPage();
////        paraBankLoginPage.clickRegisterLink();
////        assertTrue(page.url().contains("register.htm"), "Navigation to register page failed!");
////    }
//
//    @AfterMethod
//    public void handleTestFailure(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            String testName = result.getName() + " " + System.currentTimeMillis();
//            page.screenshot(new Page.ScreenshotOptions()
//                    .setPath(Paths.get("test-artifacts/screenshots/" + testName + ".png")));
//            context.tracing().stop(new Tracing.StopOptions()
//                    .setPath(Paths.get("test-artifacts/traces/" + testName + ".zip")));
//        }
//    }
//
//    @AfterClass
//    public void tearDown() {
//        BrowserSetup.closeBrowser();
//    }
//
//}
package com.example.tests;

import com.example.pages.ParaBankLoginPage;
import com.example.utils.CredentialsReader;
import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class ParaBankLoginTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    ParaBankLoginPage paraBankLoginPage;


    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        // Start tracing
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
        paraBankLoginPage = new ParaBankLoginPage(page);
    }

    @DataProvider(name = "credentials")
    public Object[][] getCredentials() throws IOException {
        Map<String, CredentialsReader.UserCredentials> credentialsMap = CredentialsReader.loadLoginCredentials();
        return new Object[][]{
                {"user1", credentialsMap.get("user1").username(), credentialsMap.get("user1").password()},
        };
    }
            @Test(dataProvider = "credentials")
    public void testLogin(String userId, String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
        String welcomeMessage = paraBankLoginPage.getWelcomeMessage();
//        assertTrue(welcomeMessage.contains("Welcome"), "Login failed for " + userId + "!");
        assert page.isVisible("text=Welcome");
        if (!welcomeMessage.contains("Welcome")) {
            page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("failure-" + userId + ".png")));
        }
    }

//    @Test()
//    public void testLogin() {
//        // Your login test code
//        page.navigate("https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC");
//        // Example: Fill login form
//        page.fill("input[name='username']", "username");
//        page.fill("input[name='password']", "password");
//        page.click("input[value='Log In']");
//        // Assert login success
//        assert page.isVisible("text=Welcome");
//    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("test-artifacts/screenshots/failure-" + result.getName() + ".png")));
            // Stop tracing and save
            try {
                context.tracing().stopChunk(new Tracing.StopChunkOptions()
                        .setPath(Paths.get("test-artifacts/traces/trace-" + result.getName() + ".zip")));
            } catch (Exception e) {
                System.err.println("Failed to stop tracing: " + e.getMessage());
            }
        }
        // Clean up
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
