package com.example.tests;

import com.example.pages.ParaBankLoginPage;
import com.example.pages.ParaBankRegistrationPage;
import com.example.utils.BrowserSetup;
import com.example.utils.CredentialsReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.testng.AssertJUnit.assertTrue;

public class ParaBankRegistrationTest {
   private BrowserContext context;
   private Page page;
   private ParaBankLoginPage paraBankLoginPage;
   ParaBankRegistrationPage paraBankRegistrationPage;

    @BeforeClass
    public void setUp() {
       context = BrowserSetup.getBrowserContext();
       page = context.newPage();
       paraBankLoginPage = new ParaBankLoginPage(page);
       paraBankRegistrationPage = new ParaBankRegistrationPage(page);
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
//                {
//                    "user2",
//                        dataMap.get("user2").getFirstName(),
//                        dataMap.get("user2").getLastName(),
//                        dataMap.get("user2").getAddress(),
//                        dataMap.get("user2").getCity(),
//                        dataMap.get("user2").getState(),
//                        dataMap.get("user2").getZipCode(),
//                        dataMap.get("user2").getPhone(),
//                        dataMap.get("user2").getSsn(),
//                        dataMap.get("user2").getPassword()
//                }
        };
    }

    @Test(dataProvider = "registrationData")
    public void testRegistration(String userId, String firstName, String lastName, String address, String city,
                                 String state, String zipCode, String phone, String ssn, String password) {
       paraBankLoginPage.navigateToLoginPage();
       paraBankLoginPage.clickRegisterLink();
       String username = "testUser" + System.nanoTime();
       paraBankRegistrationPage.register(username, firstName, lastName, address, city, state, zipCode,
               phone, ssn, password);
       String welcomeMessage = paraBankRegistrationPage.getWelcomeRegistrationMessage();
       assertTrue( welcomeMessage.contains("Welcome"));
    }

    @AfterMethod
    public void handleTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Screenshot code
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-artifacts/screenshots/failure.png")));
            // Tracing code
            context.tracing().stopChunk(new Tracing.StopChunkOptions()
                    .setPath(Paths.get("test-artifacts/traces/trace.zip")));
        }
    }

    @AfterClass
    public void tearDown() {
        BrowserSetup.closeBrowser();
    }
}
