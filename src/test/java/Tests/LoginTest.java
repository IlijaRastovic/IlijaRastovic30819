package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() {

        // Create a new instance of Firefox browser before each test method
        driver = new ChromeDriver();


        // Maximize browser window for better visibility and stability of tests
        driver.manage().window().maximize();

        // Navigate to the SauceDemo application URL
        driver.get("https://www2.link-elearning.com/linkdl/portal/signIn.php?hSajt=584826e70485130530f7f01a973d5637");

        // Set implicit wait to handle element loading delays (applies globally to driver)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));


        // Initialize page objects with current WebDriver instance
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);


    }

    @Test (priority = 1)
    public void shouldLoginWithValidCredentials() {
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = excelReader.getStringData("Sheet1", rowNumber,1);

        loginPage.enterValidUsername(username);
        loginPage.enterValidPassword(password);
        loginPage.clickLoginButton();
        homePage.waitForDashboard();



        Assert.assertTrue(homePage.getActualUrl().contains("index.php"));
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());



    }


}
