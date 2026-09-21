package Base;

import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BaseTest {

    public WebDriver driver;
    public ExcelReader excelReader;
    public ExcelHelper  excelHelper;

    public LoginPage loginPage;
    public HomePage homePage;


    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        // Initialize Excel Test data reader
        excelReader = new ExcelReader("src/test/java/TestData/DDT.xlsx");
        // Initialize helper methods for working with Excel data
        excelHelper = new ExcelHelper(excelReader);

    }
    @BeforeMethod
    public void pageSetup() {

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
  @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }



}
