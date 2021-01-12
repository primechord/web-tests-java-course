package addressbook.steps;

import addressbook.model.GroupData;
import addressbook.model.Groups;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class GroupSteps extends BaseSteps {
    public GroupSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Подтвердить заполненную форму")
    public void submitForm() {
        click(By.name("submit"));
    }

    @Step("Заполнить поля формы")
    public void fillForm(GroupData groupData) {
        type(groupData.getName(), By.name("group_name"));
        type(groupData.getHeader(), By.name("group_header"));
        type(groupData.getFooter(), By.name("group_footer"));
    }

    @Step("Начать создание группы")
    public void initCreation() {
        click(By.name("new"));
    }

    @Step("Удалить выбранную группу")
    public void deleteSelected() {
        click(By.name("delete"));
    }

    @Step("Начать модификацию группы")
    public void initGroupModification() {
        click(By.name("edit"));
    }

    @Step("Подтвердить модификацию группы")
    public void submitGroupModification() {
        click(By.name("update"));
    }

    @Step("Создаем группу")
    public void create(GroupData group) {
        initCreation();
        fillForm(group);
        submitForm();
        returnToGroupPage();
    }

    @Step("Модифицируем группу")
    public void modify(GroupData groupData) {
        selectById(groupData.getId());
        initGroupModification();
        fillForm(groupData);
        submitGroupModification();
        returnToGroupPage();
    }

    @Step("Удаляем группу")
    public void delete(GroupData groupData) {
        selectById(groupData.getId());
        deleteSelected();
        returnToGroupPage();
    }

    @Step("Выбрать группу по идентификатору")
    private void selectById(int id) {
        click(By.cssSelector("input[value='" + id + "']"));
    }

    @Step("Вернуться к странице с группами")
    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    @Step("# Получить все группы со страницы")
    public Groups all() {
        Groups groups = new Groups();
        List<SelenideElement> elements = $$(By.cssSelector("span.group"));
        for (SelenideElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groups.add(new GroupData().withId(id).withName(name));
        }
        return groups;
    }

    @Step("# Посчитать количество групп на странице")
    public int count() {
        return $$(By.name("selected[]")).size();
    }

}
