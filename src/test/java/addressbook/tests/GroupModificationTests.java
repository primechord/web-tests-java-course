package addressbook.tests;

import addressbook.model.GroupData;
import addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static addressbook.enums.ReasonFailure.COMPOSITION_IS_DIFFERENT;
import static addressbook.enums.ReasonFailure.COUNT_IS_DIFFERENT;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod(description = "Создание группы в случае отсутствия")
    public void preconditions() {
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test3"));
        }
    }

    @Test(description = "Успешная модификация группы")
    public void modificationGroup() {
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData groupData = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("updated name")
                .withHeader("updated header")
                .withFooter("updated footer");

        app.goTo().groupPage();
        app.group().modify(groupData);

        assertThat(app.group().count()).describedAs(COUNT_IS_DIFFERENT.r()).isEqualTo(before.size());

        Groups after = app.db().groups();
        assertThat(after).describedAs(COMPOSITION_IS_DIFFERENT.r()).isEqualTo(before.withOut(modifiedGroup).withAdded(groupData));

        verifyGroupListInUI();
    }

}
