import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private final By usernameInput = By.name("acct");
    private final By passwordInput = By.name("pw");
    private final By loginButton = By.xpath("//input[@type='submit' and @value='login']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public HomePage login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return new HomePage(driver, wait);
    }
}
