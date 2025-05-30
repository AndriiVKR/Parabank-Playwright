package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class AccountOverviewPage extends BasePage {
    private final Locator accountOverviewHeading;
    private final Locator openNewAccountButton;

    public AccountOverviewPage(Page page) {
        super(page);
        this.accountOverviewHeading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Accounts Overview"));
        this.openNewAccountButton = page.getByText("Open New Account");
    }

    public boolean isAccountOverviewPageDisplayed() {
        accountOverviewHeading.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        return accountOverviewHeading.isVisible();
    }

    public OpenNewAccountPage clickOpenNewAccountButton() {
        clickOnElement(openNewAccountButton);
        return new OpenNewAccountPage(page);
    }
}
