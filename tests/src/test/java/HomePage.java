import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    private final By loginLink = By.linkText("login");
    private final By logoutLink = By.linkText("logout");
    private final By submitLink = By.linkText("submit");
    private final By firstStoryTitle = By.xpath("(//span[@class='titleline']/a)[1]");
    private final By userLink = By.xpath("//a[contains(@href, 'user?id=selass')]");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get(Config.get("base.url"));
    }

    public LoginPage openLoginPage() {
        click(loginLink);
        return new LoginPage(driver, wait);
    }

    public SubmitPage openSubmitPage() {
        click(submitLink);
        return new SubmitPage(driver, wait);
    }

    public UserPage openUserPage() {
        click(userLink);
        return new UserPage(driver, wait);
    }

    public void logout() {
        click(logoutLink);
    }

    public boolean isLoggedIn() {
        return driver.getPageSource().contains("logout");
    }

    public boolean firstStoryIsVisible() {
        return waitVisible(firstStoryTitle).isDisplayed();
    }
}
