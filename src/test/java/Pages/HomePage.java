package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

    public void waitForDashboard() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();

            return url.contains("f=Dashboard")
                    || url.contains("f=dashboard");
        });

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("profile-image")
                )
        );
    }

    WebDriver driver;



    public HomePage(WebDriver driver) {
        this.driver = driver;
    }


    public WebElement getProfileImage() {
        return driver.findElement(By.id("profile-image"));
    }


    public WebElement getDashboardLink() {
        return driver.findElement(By.cssSelector("a[href*='f=Dashboard']"));
    }


    public WebElement getSearchField() {
        return driver.findElement(By.id("txtSearchKeyword"));
    }


    public WebElement getSearchButton() {
        return driver.findElement(By.id("subSearch"));
    }


    public WebElement getUnreadMessagesBadge() {
        return driver.findElement(By.id("brojPorukaViewHeader"));
    }


    public WebElement getAssignedCoursesHeading() {
        return driver.findElement(By.cssSelector("h4.tab-heading"));
    }


    public WebElement getNoActiveCoursesMessage() {
        return driver.findElement(By.cssSelector("div.no-results p"));
    }


    public WebElement getProfileMenu() {
        return driver.findElement(By.cssSelector("a.nav-link.dropdown-toggle"));
    }


    public WebElement getLogoutButton() {
        return driver.findElement(By.cssSelector("form[name='logout'] input[name='subOdjava']"));
    }


    public WebElement getAiMentorQuestionField() {
        return driver.findElement(By.cssSelector("textarea.question-box"));
    }


    public WebElement getAiMentorSendButton() {
        return driver.findElement(By.cssSelector("button.message-send"));
    }

    public String getActualUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}