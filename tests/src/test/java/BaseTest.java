import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Rule
    public TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                if (driver != null) {
                    File screenshot = ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

                    String timestamp = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

                    Path target = Paths.get(
                            "build",
                            "screenshots",
                            description.getMethodName() + "-" + timestamp + ".png"
                    );

                    Files.createDirectories(target.getParent());
                    Files.copy(screenshot.toPath(), target);

                    System.out.println("Screenshot saved: " + target.toAbsolutePath());
                }
            } catch (Exception ex) {
                System.err.println("Could not take screenshot: " + ex.getMessage());
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--window-size=" +
                        Config.get("window.width") +
                        "," +
                        Config.get("window.height")
        );

        driver = new RemoteWebDriver(
                new URL(Config.get("selenium.url")),
                options
        );

        wait = new WebDriverWait(
                driver,
                Integer.parseInt(Config.get("timeout"))
        );
    }
}
