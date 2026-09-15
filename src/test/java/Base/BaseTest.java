package Base;

import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        // Set up FirefoxDriver
        WebDriverManager.firefoxdriver().setup();
        // Initialize Excel Test data reader
        excelReader = new ExcelReader("src/test/java/TestData/DDT.xlsx");
        // Initialize helper methods for working with Excel data
        excelHelper = new ExcelHelper(excelReader);

    }
    @BeforeMethod
    public void pageSetup() {

        // Create a new instance of Firefox browser before each test method
        driver = new ChromeDriver();

        // Maximize browser window for better visibility and stability of tests
        driver.manage().window().maximize();
    }

    /*@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
*/

}
