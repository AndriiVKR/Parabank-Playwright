package com.example.utils;

import io.qameta.allure.Allure;

import com.microsoft.playwright.Page;
import java.io.ByteArrayInputStream;
import java.io.File;

public class TestUtil {
    public static void takeScreenshot(Page page, String screenshotName) {
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
    }

    public static void takeScreenshot(Page page) {
        takeScreenshot(page, "Failure Screenshot");
    }

    public static void clearAllureResultsFolder() {
        clearFolder("allure-results");
    }

    public static void clearScreenshotsFolder() {
        clearFolder("test-artifacts/screenshots");
    }

    public static void clearTracesFolder() {
        clearFolder("test-artifacts/traces");
    }

    public static void clearVideosFolder() {
        clearFolder("test-artifacts/videos");
    }

    public static void clearAllTestArtifacts() {
        clearAllureResultsFolder();
        clearScreenshotsFolder();
        clearTracesFolder();
        clearVideosFolder();
    }

    // Generic folder cleaner method
    public static void clearFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    clearFolder(file.getPath()); // Recursively clear subfolders
                }
                file.delete();
            }
        }
    }

}
