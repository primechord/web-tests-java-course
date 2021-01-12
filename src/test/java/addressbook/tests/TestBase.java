package addressbook.tests;

import addressbook.model.GroupData;
import addressbook.model.Groups;
import addressbook.steps.ApplicationManager;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static addressbook.enums.ReasonFailure.COMPOSITION_IS_DIFFERENT;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() throws IOException {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] p) {
        logger.info("Start test " + method.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod
    public void logTestStop(Method method, Object[] p) {
        logger.info("Stop test " + method.getName() + " with parameters " + Arrays.asList(p));
    }

    // FIXME не прикладывать в отчет для ВЫКЛ
    @Step("Опциональное сравнение состава групп на UI и DB")
    protected void verifyGroupListInUI() {
        boolean foundProperty = Boolean.getBoolean("verifyUI");
        logger.debug("foundProperty: " + foundProperty);
        if (foundProperty) {
            Groups db = app.db().groups();
            Groups ui = app.group().all();
            assertThat(ui).describedAs(COMPOSITION_IS_DIFFERENT.r())
                    .isEqualTo(db.stream().map((g) ->
                            new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet()));
        }
    }

}
