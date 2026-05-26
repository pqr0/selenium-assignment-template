import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class UserPage extends BasePage {
    private final By aboutTextarea = By.name("about");
    private final By updateButton = By.xpath("//input[@type='submit' and @value='update']");
    private final By showDeadDropdown = By.name("showd");
    private final By noProcrastDropdown = By.name("nopro");

    public UserPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void updateProfile() {
        click(updateButton);
    }

    public void openOwnProfile() {
        driver.get(Config.get("base.url") + "/user?id=" + Config.get("username"));
    }

    public boolean aboutTextareaIsVisible() {
        return waitVisible(aboutTextarea).isDisplayed();
    }

    public void updateAboutText(String text) {
        WebElement textarea = waitVisible(aboutTextarea);
        textarea.clear();
        textarea.sendKeys(text);
        click(updateButton);
    }

    public boolean aboutTextContains(String text) {
        WebElement textarea = waitVisible(aboutTextarea);
        return textarea.getAttribute("value").contains(text);
    }

    public void setShowDead(String value) {
        Select dropdown = new Select(waitVisible(showDeadDropdown));
        dropdown.selectByVisibleText(value);
    }

    public void setNoProcrast(String value) {
        Select dropdown = new Select(waitVisible(noProcrastDropdown));
        dropdown.selectByVisibleText(value);
    }

    public String getShowDeadValue() {
        Select dropdown = new Select(waitVisible(showDeadDropdown));
        return dropdown.getFirstSelectedOption().getText();
    }
}
