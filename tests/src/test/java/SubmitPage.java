import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubmitPage extends BasePage {
    private final By titleInput = By.name("title");
    private final By urlInput = By.name("url");
    private final By textArea = By.name("text");
    private final By submitButton = By.xpath("//input[@type='submit']");

    public SubmitPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void fillSubmissionFormWithoutSubmittingPublicly() {
        type(titleInput, "Selenium assignment test");
        type(urlInput, "https://example.com");
        type(textArea, "This is textarea content for my Selenium assignment.");
    }

    public boolean textareaContainsText() {
        return waitVisible(textArea)
                .getAttribute("value")
                .contains("Selenium assignment");
    }
}
