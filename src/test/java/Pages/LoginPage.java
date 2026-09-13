package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LoginPage {

    WebDriver driver;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
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

    //-------------------------------------------------------------------


    public void enterValidUsername(String username){
        getUsernameField().clear();
        getUsernameField().sendKeys(username);
    }

    public void enterValidPassword(String password){
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickLoginButton(){
        getLoginButton().click();
    }

    public void clickForgotPassword(){
        getForgotPassword().click();
    }



    public void loginWithValidCredentials(String username, String password){
        enterValidUsername(username);
        enterValidPassword(password);
        clickLoginButton();
    }
}
