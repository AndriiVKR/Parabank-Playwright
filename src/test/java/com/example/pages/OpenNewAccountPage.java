package com.example.pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;





public class OpenNewAccountPage extends BasePage {
    private final Locator openNewAccountPageText;
    private final Locator accountTypeChecking;
    private final Locator accountTypeSavings;
    private final Locator dropDownCheckingSaving;
    private final Locator dropDownCheckingAccount;
    private final Locator dropDownSavingAccount;


    public OpenNewAccountPage(Page page) {
        super(page);
        this.openNewAccountPageText = page.getByText("Open New Account");
        this.accountTypeChecking = page.getByText("Checking");
        this.accountTypeSavings = page.getByText("Savings");
        this.dropDownCheckingSaving = page.locator("//select[@id='type']");
        this.dropDownCheckingAccount = page.locator("//select[@id='type']/option[@value='0']");
        this.dropDownSavingAccount = page.locator("//select[@id='type']/option[@value='1']");
    }

    public String getSelectedAccountType() {
        return dropDownCheckingSaving.inputValue();  // gets selected <option>'s value
    }

    public void selectCheckingAccount() {
        dropDownCheckingSaving.selectOption("0");  // "0" = CHECKING
    }

    public void selectSavingsAccount() {
        dropDownCheckingSaving.selectOption("1");  // "1" = SAVINGS
    }
}
