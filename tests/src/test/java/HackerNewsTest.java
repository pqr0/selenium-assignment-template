import static org.junit.Assert.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.junit.Test;
import org.openqa.selenium.*;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.UUID;

public class HackerNewsTest extends BaseTest {

    @Test
    public void loginAndLogoutTest() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.open();

        homePage
            .openLoginPage()
            .login(Config.get("username"), Config.get("password"));

        assertTrue(homePage.isLoggedIn());

        homePage.logout();

        assertTrue(driver.getPageSource().contains("login"));
    }

    @Test
    public void loggedInUserCanOpenSubmitFormAndFillTextareaTest() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.open();

        homePage
            .openLoginPage()
            .login(Config.get("username"), Config.get("password"));

        SubmitPage submitPage = homePage.openSubmitPage();
        submitPage.fillSubmissionFormWithoutSubmittingPublicly();

        assertTrue(submitPage.textareaContainsText());
    }

    @Test
    public void staticHomePageContentTest() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.open();

        By hackerNewsLogo = By.xpath("//a[@href='news' and text()='Hacker News']");

        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(hackerNewsLogo));
        assertEquals("Hacker News", logo.getText());
    }

    @Test
    public void multiplePageTest() {
        List<String> pages = Arrays.asList(
                "/news",
                "/newest",
                "/front",
                "/ask",
                "/show",
                "/jobs"
                );

        for (String page : pages) {
            driver.get(Config.get("base.url") + page);

            assertTrue(driver.getTitle().contains("Hacker News"));
            assertTrue(driver.getPageSource().contains("Hacker News"));
        }
    }

    @Test
    public void browserHistoryTest() {
        driver.get(Config.get("base.url") + "/news");
        assertTrue(driver.getCurrentUrl().contains("news"));

        driver.get(Config.get("base.url") + "/newest");
        assertTrue(driver.getCurrentUrl().contains("newest"));

        driver.navigate().back();
        assertTrue(driver.getCurrentUrl().contains("news"));

        driver.navigate().forward();
        assertTrue(driver.getCurrentUrl().contains("newest"));
    }

    @Test
    public void invalidLoginCookieShouldLogoutUserTest() {

        HomePage homePage = new HomePage(driver, wait);

        homePage.open();
        homePage.openLoginPage()
                .login(Config.get("username"), Config.get("password"));

        assertTrue(
                "User should be logged in before cookie manipulation",
                homePage.isLoggedIn()
        );

        driver.manage().deleteCookieNamed("user");
        driver.manage().addCookie(new Cookie("user", "0"));

        driver.navigate().refresh();

        assertTrue(
                "Login link should appear after invalidating login cookie",
                driver.getPageSource().contains("login")
        );

        assertFalse(
                "Logout link should disappear after invalidating login cookie",
                driver.getPageSource().contains("logout")
        );
    }

    @Test
    public void javascriptExecutorScrollTest() {
        driver.get(Config.get("base.url"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Long scrollPosition =
            (Long) js.executeScript("return Math.round(window.scrollY)");

        assertTrue(scrollPosition > 0);
    }


    @Test
    public void randomDataBioUpdateTest() {
        HomePage homePage = new HomePage(driver, wait);

        homePage.open();
        homePage.openLoginPage().login(Config.get("username") ,Config.get("password"));

        UserPage userPage = new UserPage(driver, wait);
        userPage.openOwnProfile();

        String randomBio = "selenium-test-" + UUID.randomUUID() .toString() .substring(0,8);
        userPage.updateAboutText(randomBio);
        assertTrue(userPage.aboutTextContains(randomBio)
        );
    }

    @Test
    public void dropdownPreferenceChangeTest() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.open();

        homePage.openLoginPage().login(Config.get("username"), Config.get("password"));

        UserPage userPage = new UserPage(driver, wait);
        userPage.openOwnProfile();

        userPage.setShowDead("yes");
        userPage.updateProfile();

        assertEquals("yes", userPage.getShowDeadValue());
    }

    //@Test
    //public void screenshotFailureDemoTest() {
        //driver.get(Config.get("base.url"));
        //fail("Intentional failure to test screenshot");
    //}
}
