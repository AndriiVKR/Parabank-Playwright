
package com.example.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class ParaBankLoginTest extends BaseTest {

    @Test(dataProvider = "credentials")
    public void testLogin(String userId, String username, String password) {
        paraBankLoginPage.navigateToLoginPage();
        paraBankLoginPage.login(username, password);
        String welcomeMessage = paraBankLoginPage.getWelcomeMessage();
        assert page.isVisible("text=Welcome");
        if (!welcomeMessage.contains("Welcome")) {
            page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("failure-" + userId + ".png")));
        }
    }
}
