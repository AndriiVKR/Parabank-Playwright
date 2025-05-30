package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

public class ParaBankLoginPage extends BasePage{

    private final Locator registerLink;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator welcomeMessage;

//    String registerLink = "text=Register";
//    String usernameField = "input[name='username']";
//    String passwordField = "input[name='password']";
//    String loginButton = "input[value='Log In']";
//    String welcomeMessage = "text=Welcome";
//    String errorMessage = "span.error";

    public ParaBankLoginPage(Page page) {
        super(page);
        this.registerLink = page.locator("text=Register");
        this.usernameField = page.locator("input[name='username']");
        this.passwordField = page.locator("input[name='password']");
        this.loginButton = page.locator("input[value='Log In']");
        this.welcomeMessage = page.locator("text=Welcome");
        this.errorMessage = page.locator("p.error");

    }
    public void navigateToLoginPage() {
        navigateTo(baseUrl);
    }

    public void clickRegisterLink() {
        clickOnElement(registerLink);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        clickOnElement(loginButton);
    }

    public String getWelcomeMessage() {
        waitForElement(welcomeMessage, new Locator.WaitForOptions()
                .setTimeout(5000));
        return getElementText(welcomeMessage);
    }

    public boolean isLoginErrorMassageDisplayed() {
        try {
            errorMessage.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            return errorMessage.isVisible();
        } catch (PlaywrightException e) {
            return false;
        }
    }

}

