package Base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
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

    // Captures a failed test before closing the browser.
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            try {
                if (result.getStatus() == ITestResult.FAILURE) {
                    saveFailureScreenshot(result);
                }
            } finally {
                driver.quit();
                driver = null;
            }
        }
    }

    private void saveFailureScreenshot(ITestResult result) {
        Path screenshot = Path.of("screenshots",
                result.getTestClass().getRealClass().getSimpleName() + "_"
                        + result.getMethod().getMethodName() + "_" + UUID.randomUUID() + ".png");

        try {
            Files.createDirectories(screenshot.getParent());
            Files.write(screenshot, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            Reporter.log("Screenshot: " + screenshot.toAbsolutePath(), true);
        } catch (IOException | WebDriverException e) {
            Reporter.log("Could not save screenshot for failed test: " + e.getMessage(), true);
        }
    }
}
