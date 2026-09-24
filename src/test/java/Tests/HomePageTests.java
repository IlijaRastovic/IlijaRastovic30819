package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.WebElement;
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
            Assert.assertTrue(homePage.getActualUrl().contains("index.php"), "Expected the portal page URL after login.");
            Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
            Assert.assertEquals(homePage.getPageTitle(), "Link Elearning", "Expected the portal page title to be Link Elearning.");
            Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");
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

        Assert.assertTrue(homePage.isDarkModeEnabled(), "Expected dark mode to be enabled after saving settings.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning", "Expected the portal page title to be Link Elearning.");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");

    }


    @Test(priority = 20)
    public void shouldCloseLiveClassPanel(){
        logInAsValidUser();
        if (!homePage.isLiveClassPanelVisible()) {
            homePage.openDashboardSettings();
            homePage.toggleLiveClassPanel();
            homePage.clickSaveSettingsButton();
            homePage.waitForLiveClassPanelToBeVisible();
        }
        homePage.closeLiveClassPanel();

        Assert.assertFalse(homePage.isLiveClassPanelVisible(), "Expected the live class panel to disappear after closing it.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");
    }

    @Test(priority = 30)
    public void shouldShowLiveClassPanel() {
        logInAsValidUser();
        if (homePage.isLiveClassPanelVisible()) {
            homePage.closeLiveClassPanel();
        }
        homePage.openDashboardSettings();
        homePage.toggleLiveClassPanel();
        homePage.clickSaveSettingsButton();
        homePage.waitForLiveClassPanelToBeVisible();

        Assert.assertTrue(homePage.isLiveClassPanelVisible(), "Expected the live class panel to be visible after enabling it.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");
    }

}
