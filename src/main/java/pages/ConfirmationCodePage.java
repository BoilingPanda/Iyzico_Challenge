package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationCodePage extends Base {
    WebDriver driver;

    public ConfirmationCodePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    By smsCodeInput = By.id("smsCode");
    By submitButton = By.id("submitBtn");
    By cancelButton = By.id("cancelBtn");
    By smsCode = By.className("help-block");

    public WebElement getSmsCodeInput() {
        return driver.findElement(smsCodeInput);
    }

    public WebElement getSmsCode() {
        return driver.findElement(smsCode);
    }

    public WebElement getSubmitButton() {
        return driver.findElement(submitButton);
    }

    public WebElement getCancelButton() {
        return driver.findElement(cancelButton);
    }

    public void fillSmsCodeInput() {
        String smsCodeText = getSmsCode().getText();
        smsCodeText = getSmsCodeText(smsCodeText);
        getSmsCodeInput().sendKeys(smsCodeText);
    }

    private String getSmsCodeText(String text) {
        return text.replaceAll(".*\\(|\\).*", "");
    }

    public void clickSubmitButton() {
        getSubmitButton().click();
        tearDown();
    }

}
