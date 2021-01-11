package addressbook.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class NavigationSteps extends BaseSteps {
    public NavigationSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Перейти к странице с группами")
    public void groupPage() {
        if ($(By.tagName("h1")).exists()
                && $(By.tagName("h1")).getText().equals("Groups")
                && $(By.name("new")).exists()) return;
        click(By.linkText("groups"));
    }

}
