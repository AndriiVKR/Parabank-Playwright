package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.sql.SQLOutput;

public class ParaBankRegistrationPage {

    private final Page page;
    private final String firstNameField = "input[name='customer.firstName']";
    private final String lastNameField = "input[name='customer.lastName']";
    private final String addressField = "input[name='customer.address.street']";
    private final String cityField = "input[name='customer.address.city']";
    private final String stateField = "input[name='customer.address.state']";
    private final String zipCodeField = "input[name='customer.address.zipCode']";
    private final String phoneField = "input[name='customer.phoneNumber']";
    private final String ssnField = "input[name='customer.ssn']";
    private final String userNameField = "input[name='customer.username']";
    private final String passwordField = "input[name='customer.password']";
    private final String passwordConfirmField = "input[name='repeatedPassword']";
    private final String registerButton = "input.button[type='submit'][value='Register']";
    private final String errorMessage = "span.error";
    public ParaBankRegistrationPage(Page page) {
        this.page = page;
    }

    public void register(String firstName, String lastName, String address, String city, String state, String zipCode,
                         String phone, String ssn, String userName, String password) {
        System.out.println("Filling registration form for username: " + userName);
        page.waitForSelector(firstNameField, new Page.WaitForSelectorOptions()
                .setTimeout(5000));
        page.fill(firstNameField, firstName);
        page.fill(lastNameField, lastName);
        page.fill(addressField, address);
        page.fill(cityField, city);
        page.fill(stateField, state);
        page.fill(zipCodeField, zipCode);
        page.fill(phoneField, phone);
        page.fill(ssnField, ssn);
        page.fill(userNameField, userName);
        page.fill(passwordField, password);
        page.fill(passwordConfirmField, password);
        page.click(registerButton);
        System.out.println("Clicked Register button");
        Locator error = page.locator(errorMessage);
        if (error.isVisible()) {
            System.out.println("Error message is visible");
            String errorMessage = error.textContent();
            System.out.println("Error message: " + errorMessage);
            throw new RuntimeException("Registration failed: " + errorMessage);
        }
        page.waitForURL("https://parabank.parasoft.com/parabank/register.htm", new Page.WaitForURLOptions()
                .setTimeout(5000));
    }

    public String getWelcomeRegistrationMessage() {
        page.waitForSelector("p.smallText", new Page.WaitForSelectorOptions()
                .setTimeout(5000));
        String message = page.locator("p.smallText").textContent();
        System.out.println("Welcome message: " + message);
        return message;
    }
}
