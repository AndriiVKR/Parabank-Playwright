package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ParaBankRegistrationPage extends BasePage {

    private final Page page;

    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator addressField;
    private final Locator cityField;
    private final Locator stateField;
    private final Locator zipCodeField;
    private final Locator phoneField;
    private final Locator ssnField;
    private final Locator userNameField;
    private final Locator passwordField;
    private final Locator passwordConfirmField;
    private final Locator registerButton;
    private final Locator errorMessage;
    private final Locator welcomeMessage;

//    private final String registerUrl = "register.url"; // ✅ Update if needed

    public ParaBankRegistrationPage(Page page) {
        super(page);
        this.page = page;
        this.firstNameField = page.locator("input[name='customer.firstName']");
        this.lastNameField = page.locator("input[name='customer.lastName']");
        this.addressField = page.locator("input[name='customer.address.street']");
        this.cityField = page.locator("input[name='customer.address.city']");
        this.stateField = page.locator("input[name='customer.address.state']");
        this.zipCodeField = page.locator("input[name='customer.address.zipCode']");
        this.phoneField = page.locator("input[name='customer.phoneNumber']");
        this.ssnField = page.locator("input[name='customer.ssn']");
        this.userNameField = page.locator("input[name='customer.username']");
        this.passwordField = page.locator("input[name='customer.password']");
        this.passwordConfirmField = page.locator("input[name='repeatedPassword']");
        this.registerButton = page.locator("input.button[type='submit'][value='Register']");
        this.errorMessage = page.locator("span.error");
        this.welcomeMessage = page.locator("p.smallText");
    }

    public void register(String firstName, String lastName, String address, String city, String state, String zipCode,
                         String phone, String ssn, String userName, String password) {
        System.out.println("Filling registration form for username: " + userName);
        firstNameField.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(addressField, address);
        type(cityField, city);
        type(stateField, state);
        type(zipCodeField, zipCode);
        type(phoneField, phone);
        type(ssnField, ssn);
        type(userNameField, userName);
        type(passwordField, password);
        type(passwordConfirmField, password);
        clickOnElement(registerButton);

        System.out.println("Clicked Register button");

        if (errorMessage.isVisible()) {
            String error = errorMessage.textContent().trim();
            System.out.println("Error message: " + error);
            throw new RuntimeException("Registration failed: " + error);
        }

        page.waitForURL(registerUrl, new Page.WaitForURLOptions().setTimeout(5000));
    }

    public String getWelcomeRegistrationMessage() {
        waitForElement(welcomeMessage, new Locator.WaitForOptions().setTimeout(5000));
        String message = getElementText(welcomeMessage);
        System.out.println("Welcome message: " + message);
        return message;
    }
}

//public class ParaBankRegistrationPage extends BasePage {
//
//    private final Page page;
//    private final String firstNameField = "input[name='customer.firstName']";
//    private final String lastNameField = "input[name='customer.lastName']";
//    private final String addressField = "input[name='customer.address.street']";
//    private final String cityField = "input[name='customer.address.city']";
//    private final String stateField = "input[name='customer.address.state']";
//    private final String zipCodeField = "input[name='customer.address.zipCode']";
//    private final String phoneField = "input[name='customer.phoneNumber']";
//    private final String ssnField = "input[name='customer.ssn']";
//    private final String userNameField = "input[name='customer.username']";
//    private final String passwordField = "input[name='customer.password']";
//    private final String passwordConfirmField = "input[name='repeatedPassword']";
//    private final String registerButton = "input.button[type='submit'][value='Register']";
//    private final String errorMessage = "span.error";
//    private final String welcomeMessage = "p.smallText";
//
//    public ParaBankRegistrationPage(Page page) {
//        super(page);
//        this.page = page;
//    }
//
//    public void register(String firstName, String lastName, String address, String city, String state, String zipCode,
//                         String phone, String ssn, String userName, String password) {
//        System.out.println("Filling registration form for username: " + userName);
//        page.waitForSelector(firstNameField, new Page.WaitForSelectorOptions()
//                .setTimeout(5000));
//        type(firstNameField, firstName);
//        type(lastNameField, lastName);
//        type(addressField, address);
//        type(cityField, city);
//        type(stateField, state);
//        type(zipCodeField, zipCode);
//        type(phoneField, phone);
//        type(ssnField, ssn);
//        type(userNameField, userName);
//        type(passwordField, password);
//        type(passwordConfirmField, password);
//        clickOnElement(registerButton);
//        System.out.println("Clicked Register button");
//        Locator error = page.locator(errorMessage);
//        if (error.isVisible()) {
//            System.out.println("Error message is visible");
//            String errorMessage = error.textContent();
//            System.out.println("Error message: " + errorMessage);
//            throw new RuntimeException("Registration failed: " + errorMessage);
//        }
//        page.waitForURL(registerUrl, new Page.WaitForURLOptions()
//                .setTimeout(5000));
//    }
//
//    public String getWelcomeRegistrationMessage() {
//        waitForElement(welcomeMessage, new Page.WaitForSelectorOptions()
//                .setTimeout(5000));
//        String message = getElementText(welcomeMessage);
//        System.out.println("Welcome message: " + message);
//        return message;
//    }
//}
