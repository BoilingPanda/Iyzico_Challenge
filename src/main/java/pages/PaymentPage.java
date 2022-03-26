package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaymentPage extends Base {
    WebDriver driver;


    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    By creditCardTab = By.id("iyz-tab-credit-card");
    By iyzicoTab = By.id("iyz-tab-payWithIyzico");
    By othersTab = By.id("iyz-tab-apm");

    By paymentButton = By.id("iyz-payment-button");
    By cardInput = By.id("ccnumber");
    By nameInput = By.id("ccname");
    By expDateInput = By.id("ccexp");
    By cvcInput = By.id("cccvc");
    By threeDSCheckbox = By.id("iyz-checkbox-threeDS");
    By registerCardCheckbox = By.id("iyz-checkbox-registerCard");
    By endUserAgreement = By.cssSelector(".e18tja753 a");
    By installment = By.id("iyziInstallmentList");

    By payWithIyzico = By.cssSelector(".e1m1rt1n0");

    By otherPaymentsSelect = By.id("iyz-select-button");

    public WebElement getCreditCardTab() {
        return driver.findElement(creditCardTab);
    }

    public WebElement getIyzicoTab() {
        return driver.findElement(iyzicoTab);
    }

    public WebElement getOthersTab() {
        return driver.findElement(othersTab);
    }

    public WebElement getPaymentButton() {
        return driver.findElement(paymentButton);
    }

    public WebElement getCardInput() {
        return driver.findElement(cardInput);
    }

    public WebElement getNameInput() {
        return driver.findElement(nameInput);
    }

    public WebElement getExpDateInput() {
        return driver.findElement(expDateInput);
    }

    public WebElement getCvcInput() {
        return driver.findElement(cvcInput);
    }

    public WebElement getThreeDSCheckbox() {
        return driver.findElement(threeDSCheckbox);
    }

    public WebElement getRegisterCardCheckbox() {
        return driver.findElement(registerCardCheckbox);
    }

    public WebElement getEndUserAgreement() {
        return driver.findElement(endUserAgreement);
    }

    public WebElement getInstallmentSection() {
        return driver.findElement(installment);
    }


    public WebElement getPayWithIyzicoButton() {
        return driver.findElement(payWithIyzico);
    }

    public WebElement getOtherPaymentsSelect() {
        return driver.findElement(otherPaymentsSelect);
    }

    public ConfirmationCodePage clickPaymentButton() {
        getPaymentButton().click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("smsCode")));
        return new ConfirmationCodePage(driver);
    }

    public void fillCardInput(String cardNumber) {
        getCardInput().sendKeys(cardNumber);
    }

    public void fillCardNameInput(String cardName) {
        getNameInput().sendKeys(cardName);
    }

    public void fillExpDateInput(String expDate) {
        getExpDateInput().sendKeys(expDate);
    }

    public void fillCvcInput(String cvcNumber) {
        getCvcInput().sendKeys(cvcNumber);
    }

    public void clickThreeDS() {
        getThreeDSCheckbox().click();
    }

    public void clickRegisterCard() {
        getRegisterCardCheckbox().click();
    }

    public void clickEndUserAgreement() {
        getEndUserAgreement().click();
    }

}
