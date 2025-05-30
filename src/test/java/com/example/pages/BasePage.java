package com.example.pages;

import com.example.utils.ConfigReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public abstract class BasePage {
    protected final Page page;

    public void takeScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots", fileName + ".png"))
                .setFullPage(true));
    }


    String baseUrl = ConfigReader.get("base.url");
    protected String registerUrl = ConfigReader.get("register.url");

    public BasePage(Page page) {
        this.page = page;
    }

    public void clickOnElement(Locator selector) {
        selector.click();
    }

    public void type(Locator selector, String text) {
        selector.fill(text);
    }

    public void waitForElement(Locator selector, Locator.WaitForOptions waitForOptions) {
        selector.waitFor(waitForOptions);
    }

    public boolean isElementVisible(Locator selector) {
        selector.isVisible();
        return true;
    }

    public String getElementText(Locator selector) {
        return selector.textContent();
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }
}
