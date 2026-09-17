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


    public WebElement getLiveClassPanel() {
        return driver.findElement(By.id("panel_101"));
    }

    public WebElement getMessagesPanel() {
        return driver.findElement(By.id("panel_102"));
    }

    public WebElement getWhiteboardPanel() {
        return driver.findElement(By.id("panel_103"));
    }

    public WebElement getChatPanel() {
        return driver.findElement(By.id("panel_105"));
    }

    public WebElement getCvPanel() {
        return driver.findElement(By.id("panel_106"));
    }

    public WebElement getOtherServicesPanel() {
        return driver.findElement(By.id("panel_107"));
    }

    public WebElement getTechnicalSupportPanel() {
        return driver.findElement(By.id("panel_108"));
    }

    public WebElement getDashboardSettingsButton() {
        return driver.findElement(By.cssSelector("#panel_101 i.podesavanja-popup-open"));
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

    public WebElement getDarkModeCheckBox() {
        return driver.findElement(By.cssSelector("input[type='checkbox'][name='theme_mode']"));
    }

    public WebElement getSaveSettingsButton() {
        return driver.findElement(By.cssSelector("button.yes-btn"));
    }

    //------------------------------------------------------------------------------------------

    public void openDashboardSettings() {
        getDashboardSettingsButton().click();
    }

    public void toggleDarkModeCheckBox() {
        if(!getDarkModeCheckBox().isSelected()) {
            getDarkModeCheckBox().click();
        };
    }

    public void clickSaveSettingsButton() {
        getSaveSettingsButton().click();
    }

    public boolean isDarkModeEnabled() {
        return !driver.findElements(
                By.cssSelector("link[href*='/css-dark-mode/style-its.css']")
        ).isEmpty();
    }
}
