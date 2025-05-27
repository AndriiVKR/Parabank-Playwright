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

public class ParaBankRegistrationTest extends BaseTest{


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

}
