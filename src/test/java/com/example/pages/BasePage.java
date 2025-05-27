package com.example.pages;

import com.example.utils.ConfigReader;
import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected final Page page;

    String baseUrl = ConfigReader.get("base.url");
    protected String registerUrl = ConfigReader.get("register.url");

    public BasePage(Page page) {
        this.page = page;
    }

    public void clickOnElement(String selector) {
        page.locator(selector).click();
    }

    public void type(String selector, String text) {
        page.locator(selector).fill(text);
    }

    public void waitForElement(String selector, Page.WaitForSelectorOptions waitForSelectorOptions) {
        page.waitForSelector(selector);
    }

    public boolean isElementVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    public String getElementText(String selector) {
        return page.locator(selector).textContent();
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }
}
