package Base;

import java.io.IOException;

import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    public WebDriver driver;
    public ExcelReader excelReader;

    public LoginPage loginPage;
    public HomePage homePage;

    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();

        excelReader = new ExcelReader("src/test/java/TestData/DDT.xlsx");
    }

    @BeforeMethod
    public void pageSetup() {
        // The -Dheadless=true parameter starts Chrome without a visible window.
        boolean headless = Boolean.getBoolean("headless");
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    // Closes the browser even if the test or its setup fails.
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
