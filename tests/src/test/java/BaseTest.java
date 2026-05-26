import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--window-size=" + Config.get("window.width") + "," + Config.get("window.height"));

        driver = new RemoteWebDriver(new URL(Config.get("selenium.url")), options );

        wait = new WebDriverWait(driver, Integer.parseInt(Config.get("timeout")));
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}