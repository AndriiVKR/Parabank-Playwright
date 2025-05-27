package com.example.tests;

import com.example.pages.AccountOverviewPage;
import com.example.pages.ParaBankLoginPage;
import com.example.pages.ParaBankRegistrationPage;
import com.example.utils.BrowserSetup;
import com.example.utils.CredentialsReader;
import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class BaseTest extends BrowserSetup{

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected ParaBankLoginPage paraBankLoginPage;
    protected ParaBankRegistrationPage paraBankRegistrationPage;
    protected AccountOverviewPage accountOverviewPage;



    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserType) {
        context = BrowserSetup.getBrowserContext(browserType);
        page = context.newPage();

        paraBankLoginPage = new ParaBankLoginPage(page);
        paraBankRegistrationPage = new ParaBankRegistrationPage(page);
        accountOverviewPage = new AccountOverviewPage(page);
    }

    @DataProvider(name = "credentials")
    public Object[][] getCredentials() throws IOException {
        Map<String, CredentialsReader.UserCredentials> credentialsMap = CredentialsReader.loadLoginCredentials();
        return new Object[][]{
                {"user1", credentialsMap.get("user1").username(), credentialsMap.get("user1").password()},
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

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("test-artifacts/screenshots/failure-" + result.getName() + ".png")));
            try {
                context.tracing().stopChunk(new Tracing.StopChunkOptions()
                        .setPath(Paths.get("test-artifacts/traces/trace-" + result.getName() + ".zip")));
            } catch (Exception e) {
                System.err.println("Failed to stop tracing: " + e.getMessage());
            }
        }
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

}
