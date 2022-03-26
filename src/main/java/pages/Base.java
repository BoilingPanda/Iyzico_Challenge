package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class Base {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public static PaymentPage goToCheckoutForm(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("iyz-payment-button")));
        return new PaymentPage(driver);
    }

    public void tearDown() {
        driver.quit();
    }

}
