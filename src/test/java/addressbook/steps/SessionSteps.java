package addressbook.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionSteps extends BaseSteps {
    public SessionSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Войти по логину и паролю")
    public void login(String login, String password) {
        type(login, By.name("user"));
        type(password, By.name("pass"));
        click(By.cssSelector("input:nth-child(7)"));
    }

}
