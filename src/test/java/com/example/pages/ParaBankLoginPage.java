package com.example.pages;
import com.example.utils.ConfigReader;
import com.microsoft.playwright.Page;

public class ParaBankLoginPage extends BasePage{

    String registerLink = "text=Register";
    String usernameField = "input[name='username']";
    String passwordField = "input[name='password']";
    String loginButton = "input[value='Log In']";
    String welcomeMessage = "p.smallText";

    public ParaBankLoginPage(Page page) {
        super(page);
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
        waitForElement(welcomeMessage, new Page.WaitForSelectorOptions()
                .setTimeout(5000));
        return getElementText(welcomeMessage);
    }
}

