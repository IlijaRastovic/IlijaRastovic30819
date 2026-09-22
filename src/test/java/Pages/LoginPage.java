package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Sends TAB to the element that currently has focus.
    public void pressTab() {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    public WebElement getUsernameField() {
        return driver.findElement(By.id("username"));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.id("password"));
    }

    public WebElement getLoginButton() {
        return driver.findElement(By.id("submit"));
    }

    public WebElement getForgotPassword() {
        return driver.findElement(By.id("prikazi_reset_formu"));
    }

    public WebElement getErrorMsg() {
        return driver.findElement(By.xpath(
                "//div[contains(text(), "
                + "'pogrešno korisničko ime ili šifru')]"
        ));
    }

    // Keeps the login URL check independent of the optional notice parameter and URL fragment.
    public String getExpectedLoginUrl() {
        return "https://www2.link-elearning.com/linkdl/portal/signIn.php"
                + "?hSajt=584826e70485130530f7f01a973d5637";
    }

    public String getActualLoginUrl() {
        return driver.getCurrentUrl();
    }

    public void enterUsername(String username) {
        getUsernameField().clear();
        getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickLoginButton() {
        getLoginButton().click();
    }

    public void shouldLogIn(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
