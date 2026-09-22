package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class E2E extends BaseTest {

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

    @Test (priority = 10)
    public void shouldLogOut(){
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = excelReader.getStringData("Sheet1", rowNumber,1);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password");
        loginPage.clickLoginButton();
        homePage.waitForDashboard();

        Assert.assertTrue(homePage.getActualUrl().contains("index.php"));
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());

        homePage.clickProfileImage();
        homePage.clickLogOutButton();

        Assert.assertTrue(loginPage.getActualLoginUrl().startsWith(loginPage.getExpectedLoginUrl()));
        Assert.assertTrue(loginPage.getUsernameField().isDisplayed());
        Assert.assertTrue(loginPage.getPasswordField().isDisplayed());

    }

    @Test(priority = 20)
    public void shouldDeleteFirstMessage(){
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = excelReader.getStringData("Sheet1", rowNumber,1);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password");
        loginPage.clickLoginButton();
        homePage.waitForDashboard();

        Assert.assertTrue(homePage.getActualUrl().contains("index.php"));
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());

        homePage.clickNewMessagesLink();
        homePage.clickFirstMessage();
        homePage.clickOpenedMessageDeleteButton();
        homePage.confirmDeletePopUpMessasge();
        homePage.confirmMessageDeletedPopUp();

        Assert.assertTrue(homePage.isOpenedMessageDeleted(),"Opened message was not deleted.");

        homePage.clickProfileImage();
        homePage.clickLogOutButton();

        Assert.assertTrue(loginPage.getActualLoginUrl().startsWith(loginPage.getExpectedLoginUrl()));
        Assert.assertTrue(loginPage.getUsernameField().isDisplayed());
        Assert.assertTrue(loginPage.getPasswordField().isDisplayed());

    }


    @Test (priority = 30)
    public void shouldAskAiMentorAQuestion(){
        int rowNumber = 1;
        String username = excelReader.getStringData("Sheet1", rowNumber,0);
        String password = excelReader.getStringData("Sheet1", rowNumber,1);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        Assert.assertEquals(loginPage.getPasswordField().getDomAttribute("type"),"password");
        loginPage.clickLoginButton();
        homePage.waitForDashboard();

        Assert.assertTrue(homePage.getActualUrl().contains("index.php"));
        Assert.assertTrue(homePage.getActualUrl().contains("f=dashboard"));
        Assert.assertEquals(homePage.getPageTitle(), "Link Elearning");
        Assert.assertTrue(homePage.getProfileImage().isDisplayed());

        homePage.clickAiMentorOpenButton();
        homePage.enterAiMentorQuestion("Koji AI model koristis");
        homePage.clickAiMentorSendButton();
        homePage.waitForAiMentorResponse();
        homePage.clickAiMentorCloseButton();

        homePage.clickProfileImage();
        homePage.clickLogOutButton();

        Assert.assertTrue(loginPage.getActualLoginUrl().startsWith(loginPage.getExpectedLoginUrl()));
        Assert.assertTrue(loginPage.getUsernameField().isDisplayed());
        Assert.assertTrue(loginPage.getPasswordField().isDisplayed());


    }




}
