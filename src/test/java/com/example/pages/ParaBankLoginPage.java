package com.example.pages;
import com.microsoft.playwright.Page;

public class ParaBankLoginPage {
    private Page page;
    String registerLink = "text=Register";
    String usernameField = "input[name='username']";
    String passwordField = "input[name='password']";
    String loginButton = "input[value='Log In']";

    public ParaBankLoginPage(Page page) {
        this.page = page;
    }
    public void navigateToLoginPage() {
        page.navigate("https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC");
    }

    public void clickRegisterLink() {
        page.click(registerLink);
    }

    public void login(String username, String password) {
        page.fill(usernameField, username);
        page.fill(passwordField, password);
        page.click(loginButton);
    }

    public String getWelcomeMessage() {
        page.waitForSelector("p.smallText", new Page.WaitForSelectorOptions()
                .setTimeout(5000));
        return page.locator("p.smallText").textContent();
    }

}

