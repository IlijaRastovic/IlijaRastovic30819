package Pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private static final By LIVE_CLASS_CHECKBOX =
            By.cssSelector("input[type='checkbox'][name='show_portal_box[101]']");
    private static final By SAVE_SETTINGS_BUTTON = By.cssSelector("button.yes-btn");

    // Selects the title link that is a direct child of li.message, skipping the empty header link.
    private static final By FIRST_MESSAGE_LINK =
            By.cssSelector("#mess-content ul.messages > li.message:first-of-type > a.uvodnaPoruka");
    private static final By OPENED_MESSAGE_DELETE_BUTTON =
            By.cssSelector("div.message-view button.obrisi-poruku[title='Obriši']");
    private static final By OPENED_MESSAGE =
            By.cssSelector("#mess-content div.message.message-view");
    private static final By AI_MENTOR_RESPONSE =
            By.cssSelector("section.chatbox-popup div.ai-message");

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();

            return url.contains("f=Dashboard") || url.contains("f=dashboard");
        });

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile-image")));
    }

    public WebElement getProfileImage() {
        return driver.findElement(By.id("profile-image"));
    }

    public WebElement getLiveClassPanel() {
        return driver.findElement(By.id("panel_101"));
    }

    // A hidden panel may be removed from the DOM, so an empty element list means false.
    public boolean isLiveClassPanelVisible() {
        return driver.findElements(By.id("panel_101"))
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }

    public WebElement getDashboardSettingsButton() {
        return driver.findElement(By.cssSelector("#panel_102 i.podesavanja-popup-open"));
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

    public WebElement getLiveClassCloseButton() {
        return getLiveClassPanel().findElement(By.cssSelector("i.icon-close"));
    }

    public WebElement getNewMessagesLink() {
        return driver.findElement(By.cssSelector("a[href*='c=licnePoruke']"));
    }

    public WebElement getAiMentorOpenButton() {
        return driver.findElement(By.cssSelector("button.chatbox-open"));
    }

    public WebElement getAiMentorResponse() {
        return driver.findElement(AI_MENTOR_RESPONSE);
    }

    public WebElement getAiMentorCloseButton() {
        return driver.findElement(By.cssSelector(".chatbox-close"));
    }

    //------------------------------------------------------------------------------------------------------------

    public void openDashboardSettings() {
        getDashboardSettingsButton().click();
    }

    // Selects the checkbox only if dark mode is not already enabled.
    public void toggleDarkModeCheckBox() {
        if (!getDarkModeCheckBox().isSelected()) {
            getDarkModeCheckBox().click();
        }
    }

    // Selects the panel visibility checkbox only if it is not already selected.
    public void toggleLiveClassPanel() {
        WebElement checkbox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(LIVE_CLASS_CHECKBOX));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSaveSettingsButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(SAVE_SETTINGS_BUTTON)).click();
    }

    public void waitForLiveClassPanelToBeVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> isLiveClassPanelVisible());
    }

    public boolean isDarkModeEnabled() {
        return !driver.findElements(
                By.cssSelector("link[href*='/css-dark-mode/style-its.css']")
        ).isEmpty();
    }

    public void closeLiveClassPanel() {
        WebElement panel = getLiveClassPanel();
        getLiveClassCloseButton().click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOf(panel));
    }

    public void clickProfileImage() {
        getProfileImage().click();
    }

    public void clickLogOutButton() {
        getLogoutButton().click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("signIn.php"));
    }

    public void clickNewMessagesLink() {
        getNewMessagesLink().click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("inbox")));
    }

    public void clickFirstMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(FIRST_MESSAGE_LINK))
                .click();
    }

    public void clickOpenedMessageDeleteButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(OPENED_MESSAGE_DELETE_BUTTON))
                .click();
    }

    // The first JavaScript popup asks the user to confirm message deletion.
    public void confirmDeletePopUpMessasge() {
        Alert deletePopUp = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());

        deletePopUp.accept();
    }

    // The second popup confirms that the server deleted the message.
    public void confirmMessageDeletedPopUp() {
        Alert messageDeletedPopUp = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());

        messageDeletedPopUp.accept();
    }

    // The portal keeps the message container but clears its content after deletion.
    public boolean isOpenedMessageDeleted() {
        WebElement openedMessage = driver.findElement(OPENED_MESSAGE);

        boolean hasNoText = openedMessage.getText().isBlank();
        boolean hasNoDeleteButton = openedMessage.findElements(
                By.cssSelector("button.obrisi-poruku")
        ).isEmpty();

        return hasNoText && hasNoDeleteButton;
    }

    public void clickAiMentorOpenButton() {
        getAiMentorOpenButton().click();
    }

    public void enterAiMentorQuestion(String question) {
        WebElement questionField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(getAiMentorQuestionField()));

        questionField.clear();
        questionField.sendKeys(question);
    }

    public void clickAiMentorSendButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(getAiMentorSendButton()))
                .click();
    }

    // The test starts with an empty conversation and waits for the first visible response with text.
    public void waitForAiMentorResponse() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(ExpectedConditions.visibilityOfElementLocated(AI_MENTOR_RESPONSE));
        wait.until(webDriver -> !getAiMentorResponse().getText().isBlank());
    }

    public void clickAiMentorCloseButton() {
        getAiMentorCloseButton().click();
    }
}
