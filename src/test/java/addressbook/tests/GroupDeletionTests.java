package addressbook.tests;

import addressbook.model.GroupData;
import addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static addressbook.enums.ReasonFailure.COMPOSITION_IS_DIFFERENT;
import static addressbook.enums.ReasonFailure.COUNT_IS_DIFFERENT;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupDeletionTests extends TestBase {

    // FIXME подобных шагов нет в отчете
    @BeforeMethod(description = "Создание группы в случае отсутствия")
    public void preconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test(description = "Успешное удаление группы")
    public void deleteGroup() {
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);

        assertThat(app.group().count()).describedAs(COUNT_IS_DIFFERENT.r()).isEqualTo(before.size() - 1);

        Groups after = app.db().groups();
        assertThat(after).describedAs(COMPOSITION_IS_DIFFERENT.r()).isEqualTo(before.withOut(deletedGroup));

        verifyGroupListInUI();
    }

}
