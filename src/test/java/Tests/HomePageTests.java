package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePageTests extends BaseTest {

    private WebDriverWait wait;

    @BeforeMethod
    public void pageSetUp() {

        // Navigate to the SauceDemo application URL
        driver.get("https://www2.link-elearning.com/linkdl/portal/signIn.php?hSajt=584826e70485130530f7f01a973d5637");

        // Set implicit wait to handle element loading delays (applies globally to driver)
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Initialize page objects with current WebDriver instance
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    private void logInAsValidUser(){
            String username = excelReader.getStringData("Sheet1", 1,0);
            String password = excelReader.getStringData("Sheet1", 1,1);

            loginPage.shouldLogIn(username,password);
            homePage.waitForDashboard();
            Assert.assertTrue(homePage.getActualUrl().contains("index.php"));
            Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
            Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
            Assert.assertTrue(homePage.getProfileImage().isDisplayed());
    }

    //-----------------------------------TESTS-----------------------------------------------------------------------


    @Test (priority = 10)
    public void shouldEnableDarkMode() {
        logInAsValidUser();
        homePage.openDashboardSettings();
        wait.until(webDriver -> {
            WebElement checkbox = homePage.getDarkModeCheckBox();
            return checkbox.isDisplayed() && checkbox.isEnabled();
        });
        homePage.toggleDarkModeCheckBox();
        homePage.clickSaveSettingsButton();
        wait.until(webDriver -> homePage.isDarkModeEnabled());

        Assert.assertTrue(homePage.isDarkModeEnabled(),"Dark mode not loaded.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());

    }


    @Test(priority = 20)
    public void shouldCloseLiveClassPanel(){
        logInAsValidUser();
        homePage.closeLiveClassPanel();

       // Assert.assertFalse(homePage.isLiveClassPanelVisible(),"Live class panel is still visible after clicking close.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());
    }

    @Test(priority = 30)
    public void shouldShowLiveClassPanel() {
        logInAsValidUser();
        homePage.openDashboardSettings();
        homePage.toggleLiveClassPanel();
        homePage.clickSaveSettingsButton();

        Assert.assertTrue(homePage.isLiveClassPanelVisible(), "Live class panel is not visibe");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());
    }

}
