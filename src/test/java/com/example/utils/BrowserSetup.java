package com.example.utils;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;


public class BrowserSetup {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;

    public static BrowserContext getBrowserContext() {
        if (context == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            context = browser.newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("test-artifacts/videos/"))
                    . setRecordVideoSize(1280, 720));
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
        }
        return context;
    }

    public static void closeBrowser() {
        if (context != null) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("test-artifacts/trace.zip")));
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}

