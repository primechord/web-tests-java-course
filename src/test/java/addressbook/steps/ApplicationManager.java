package addressbook.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {
    private final Properties properties;
    private final String browser;
    WebDriver driver;

    private GroupSteps groupSteps;
    private NavigationSteps navigationSteps;
    private SessionSteps sessionSteps;
    private DatabaseSteps databaseSteps;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));

        databaseSteps = new DatabaseSteps();

        Configuration.browser = this.browser;
        Configuration.screenshots = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(false));

        Selenide.open(properties.getProperty("web.baseurl"));

        groupSteps = new GroupSteps(driver);
        navigationSteps = new NavigationSteps(driver);
        sessionSteps = new SessionSteps(driver);

        sessionSteps.login(
                properties.getProperty("web.admin.login"),
                properties.getProperty("web.admin.password"));
    }

    public void stop() {
        Selenide.closeWebDriver();
    }

    public GroupSteps group() {
        return groupSteps;
    }

    public NavigationSteps goTo() {
        return navigationSteps;
    }

    public DatabaseSteps db() {
        return databaseSteps;
    }

}
