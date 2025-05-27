package com.example.pages;

import com.microsoft.playwright.Page;

public class AccountOverviewPage {
    private Page page;
    private String accountOverviewHeading = "text='Account Overview'";

    public AccountOverviewPage(Page page) {
        this.accountOverviewHeading = accountOverviewHeading;
        this.page = page;
    }
    public boolean isAccountOverviewPageDisplayed() {
        return page.locator(accountOverviewHeading).isVisible();
    }

}
