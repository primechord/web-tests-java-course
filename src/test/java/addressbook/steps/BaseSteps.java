package addressbook.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class BaseSteps {
    public WebDriver driver;

    public BaseSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по элементу")
    protected void click(By locator) {
        $(locator).click();
    }

    @Step("Ввод в поле")
    protected void type(String text, By locator) {
        if (text != null) {
            String existingText = $(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                $(locator).clear();
                $(locator).sendKeys(text);
            }
        }
    }

}
