package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.time.Duration;

public class LoginTest extends BaseTest {


    @BeforeMethod
    public void pageSetUp() {

        // Navigate to the SauceDemo application URL
        driver.get("https://www2.link-elearning.com/linkdl/portal/signIn.php?hSajt=584826e70485130530f7f01a973d5637");

        // Set implicit wait to handle element loading delays (applies globally to driver)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // Initialize page objects with current WebDriver instance
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }


    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {

        int lastRow = excelReader.getLastRow("Sheet1");
        Object[][] data = new Object[lastRow][2];

        for (int row = 1; row <= lastRow; row++) {
            data[row - 1][0] =
                    excelReader.getStringData("Sheet1", row, 2);

            data[row - 1][1] =
                    excelReader.getStringData("Sheet1", row, 3);
        }

        return data;
    }



    @Test (priority = 2)
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



    @Test (dataProvider = "invalidCredentials", priority = 1)
    public void shouldNotLoginWithInvalidCredentials(
            String username,
            String password
    ) {
        loginPage.enterValidUsername(username);
        loginPage.enterValidPassword(password);
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getErrorMsg().isDisplayed(), "Error msg is shown");
        Assert.assertEquals(loginPage.getActualUrl(), driver.getCurrentUrl());

    }

    //Helper test
    @Test
    public void testtestInvalid() {
        int rowNumber = 1;
        String username = "blabla";
        String password = "blabla";

        loginPage.enterValidUsername(username);
        loginPage.enterValidPassword(password);
        loginPage.clickLoginButton();

        Assert.assertEquals(loginPage.getActualUrl(), driver.getCurrentUrl());


    }
}


