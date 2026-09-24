package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import TestData.CredentialBuilderHelper;
import org.testng.Assert;
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

   //------------------------------------TESTS-----------------------------------------------------------



    @Test (priority = 90)
    public void shouldLoginWithValidCredentials() {
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = excelReader.getStringData("Sheet1", rowNumber,1);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();
        homePage.waitForDashboard();

        Assert.assertTrue(homePage.getActualUrl().contains("index.php"), "Expected the portal page URL after login.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning", "Expected the portal page title to be Link Elearning.");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");

    }


    //Using Data Provider
    @Test (dataProvider = "invalidCredentials", priority = 10)
    public void shouldNotLoginWithInvalidCredentials(String username, String password) {

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getErrorMsg().isDisplayed(), "Expected an error message after unsuccessful login.");
        Assert.assertEquals(loginPage.getActualLoginUrl(), driver.getCurrentUrl(), "Expected to remain on the login page.");

    }

    @Test (priority = 20)
    public void shouldNotLoginWithEmptyPassword() {
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = "";

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getErrorMsg().isDisplayed(), "Expected an error message after unsuccessful login.");
        Assert.assertEquals(loginPage.getActualLoginUrl(), driver.getCurrentUrl(), "Expected to remain on the login page.");

    }


    @Test (priority = 30)
    public void shouldNotLoginWithEmptyUsername() {
        int rowNumber = 1;
        String username = "";
        String password = excelReader.getStringData("Sheet1", rowNumber, 1);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getErrorMsg().isDisplayed(), "Expected an error message after unsuccessful login.");
        Assert.assertEquals(loginPage.getActualLoginUrl(), driver.getCurrentUrl(), "Expected to remain on the login page.");

    }


    @Test (priority = 40)
    public void shouldNotLoginWithChangedPasswordCase() {

        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber, 0);
        String validPassword = excelReader.getStringData("Sheet1", rowNumber, 1);
        String invalidPassword = CredentialBuilderHelper.swapLetterCase(validPassword);

        loginPage.enterUsername(username);
        loginPage.enterPassword(invalidPassword);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getErrorMsg().isDisplayed(), "Expected an error message after unsuccessful login.");
        Assert.assertEquals(loginPage.getActualLoginUrl(), driver.getCurrentUrl(), "Expected to remain on the login page.");
    }

    @Test (priority = 50)
    public void shouldLoginWithChangedUsernameCase() {

        int rowNumber = 1;
        String validUsername = excelReader.getStringData("Sheet1", rowNumber, 0);
        String validPassword = excelReader.getStringData("Sheet1", rowNumber, 1);
        String invalidUsername = CredentialBuilderHelper.swapLetterCase(validUsername);

        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(validPassword);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password", "Password field should mask the entered password.");
        loginPage.clickLoginButton();
        homePage.waitForDashboard();

        Assert.assertTrue(homePage.getActualUrl().contains("index.php"), "Expected the portal page URL after login.");
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"), "Expected to remain on the dashboard.");
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning", "Expected the portal page title to be Link Elearning.");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed(), "Expected the profile image to be visible.");


    }


    @Test (priority = 9)
    public void shouldMoveFocusWithTab() {

        loginPage.getUsernameField().click();

        Assert.assertEquals(driver.switchTo().activeElement(), loginPage.getUsernameField(), "Expected focus on the username field.");

        loginPage.pressTab();

        Assert.assertEquals(driver.switchTo().activeElement(), loginPage.getPasswordField(), "Expected Tab to move focus to the password field.");

        loginPage.pressTab();

        Assert.assertEquals(driver.switchTo().activeElement(),loginPage.getLoginButton(), "Expected Tab to move focus to the login button.");

        loginPage.pressTab();

        Assert.assertEquals(driver.switchTo().activeElement(), loginPage.getForgotPassword(), "Expected Tab to move focus to the forgot password link.");
    }

}


