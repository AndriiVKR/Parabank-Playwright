package com.example.tests;

import com.example.pages.AccountOverviewPage;
import com.example.pages.OpenNewAccountPage;
import com.example.pages.ParaBankLoginPage;
import com.example.pages.ParaBankRegistrationPage;
import com.example.utils.BrowserSetup;
import com.example.utils.CredentialsReader;
import com.microsoft.playwright.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import com.example.utils.TestUtil;

@Listeners({AllureTestNg.class})
public class BaseTest extends BrowserSetup{

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected ParaBankLoginPage paraBankLoginPage;
    protected ParaBankRegistrationPage paraBankRegistrationPage;
    protected AccountOverviewPage accountOverviewPage;
    protected OpenNewAccountPage openNewAccountPage;

    @BeforeSuite
    public void clearTestArtifacts() {
        TestUtil.clearAllTestArtifacts();
    }

    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserType) {
        playwright = Playwright.create();
        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }

        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("test-artifacts/videos/"))
                .setRecordVideoSize(1280, 720));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));

        page = context.newPage();

//        context = BrowserSetup.getBrowserContext(browserType);
//        page = context.newPage();


        paraBankLoginPage = new ParaBankLoginPage(page);
        paraBankRegistrationPage = new ParaBankRegistrationPage(page);
        accountOverviewPage = new AccountOverviewPage(page);
        openNewAccountPage = new OpenNewAccountPage(page);

    }


    @DataProvider(name = "validCredentials")
    public Object[][] getCredentials() throws IOException {
        Map<String, CredentialsReader.UserCredentials> credentialsMap = CredentialsReader.loadLoginCredentials();
        return new Object[][]{
                {"user1",
                        credentialsMap.get("user1").username(),
                        credentialsMap.get("user1").password()
//                        credentialsMap.get("user1").expectedResult()
                }//valid user

        };
    }

    protected void login(String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
    }

    protected void navigateToOpenNewAccountPage(String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
        accountOverviewPage.clickOpenNewAccountButton();
        openNewAccountPage = new OpenNewAccountPage(page);
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] getInvalidCredentials() throws IOException {
        Map<String, CredentialsReader.UserCredentials> credentialsMap = CredentialsReader.loadLoginCredentials();
        return new Object[][]{
                {"user4",
                        credentialsMap.get("user4").username(),
                        credentialsMap.get("user4").password()
//                        credentialsMap.get("user4").expectedResult()
                } // invalid username
        };
    }

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() throws IOException {
        Map<String, CredentialsReader.UserRegistrationData> dataMap = CredentialsReader
                .loadRegistrationData();
        return new Object[][]{
                {
                        "user1",
                        dataMap.get("user1").getFirstName(),
                        dataMap.get("user1").getLastName(),
                        dataMap.get("user1").getAddress(),
                        dataMap.get("user1").getCity(),
                        dataMap.get("user1").getState(),
                        dataMap.get("user1").getZipCode(),
                        dataMap.get("user1").getPhone(),
                        dataMap.get("user1").getSsn(),
                        dataMap.get("user1").getPassword()
                }
        };
    }

//    @AfterMethod
//    public void tearDown(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            page.screenshot(new Page.ScreenshotOptions()
//                    .setPath(Paths.get("test-artifacts/screenshots/failure-" + result.getName() + ".png")));
//            try {
//                context.tracing().stopChunk(new Tracing.StopChunkOptions()
//                        .setPath(Paths.get("test-artifacts/traces/trace-" + result.getName() + ".zip")));
//            } catch (Exception e) {
//                System.err.println("Failed to stop tracing: " + e.getMessage());
//            }
//        }
//        if (context != null) context.close();
//        if (browser != null) browser.close();
//        if (playwright != null) playwright.close();
//    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE && page != null) {
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("test-artifacts/screenshots/failure-" + result.getName() + ".png")));
            }

            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("test-artifacts/traces/trace-" + result.getName() + ".zip")));
                context.close();
            }

            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        } catch (Exception e) {
            System.err.println("Teardown error: " + e.getMessage());
        }
    }
}
