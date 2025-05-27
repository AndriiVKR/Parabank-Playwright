package com.example.tests;

import org.testng.annotations.*;
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
