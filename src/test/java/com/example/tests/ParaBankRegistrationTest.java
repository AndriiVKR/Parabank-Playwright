package com.example.tests;

import io.qameta.allure.*;
import org.testng.annotations.*;
import static org.testng.AssertJUnit.assertTrue;

public class ParaBankRegistrationTest extends BaseTest{

    @Epic("New User should be able to register")
    @Feature("Registration")
    @Story("New User should be able to register")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "registrationData")
    public void testRegistration(String userId, String firstName, String lastName, String address, String city,
                                 String state, String zipCode, String phone, String ssn, String password) {
       Allure.step("Navigating to login page");
       paraBankLoginPage.navigateToLoginPage();
       Allure.step("Clicking register link");
       paraBankLoginPage.clickRegisterLink();
       String username = "testUser" + System.nanoTime();
       Allure.step("Registering new user");
       paraBankRegistrationPage.register(username, firstName, lastName, address, city, state, zipCode,
               phone, ssn, password);
       String welcomeMessage = paraBankRegistrationPage.getWelcomeRegistrationMessage();
       assertTrue( welcomeMessage.contains("Welcome"));
    }

}
