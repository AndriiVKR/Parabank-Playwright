package com.example.pages;

import com.microsoft.playwright.Page;

public class AccountOverviewPage extends BasePage{
    private Page page;
    private final String accountOverviewHeading = "text='Account Overview'";

    public AccountOverviewPage(Page page) {
        super(page);
    }

    public boolean isAccountOverviewPageDisplayed() {
        return isElementVisible(accountOverviewHeading);
    }

}
