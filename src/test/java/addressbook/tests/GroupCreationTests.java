package addressbook.tests;

import addressbook.model.GroupData;
import addressbook.model.Groups;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static addressbook.enums.ReasonFailure.COMPOSITION_IS_DIFFERENT;
import static addressbook.enums.ReasonFailure.COUNT_IS_DIFFERENT;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"));) {
            StringBuilder xml = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                xml.append(line);
                line = reader.readLine();
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml.toString());
            return groups.stream().map((g) -> new Object[]{(g)}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.json"));) {
            StringBuilder json = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json.toString(), new TypeToken<List<GroupData>>() { }.getType());
            return groups.stream().map((g) -> new Object[]{(g)}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validGroupsFromJson", description = "Успешное создание группы")
    public void createGroup(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().initCreation();
        app.group().fillForm(group);
        app.group().submitForm();
        app.group().returnToGroupPage();

        assertThat(app.group().count()).describedAs(COUNT_IS_DIFFERENT.r()).isEqualTo(before.size() + 1);

        Groups after = app.db().groups();
        assertThat(after).describedAs(COMPOSITION_IS_DIFFERENT.r()).isEqualTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt())));

        verifyGroupListInUI();
    }

    @Test(description = "Невалидный символ в названии создаваемой группы")
    public void groupWithBadNameNotCreated() {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().initCreation();
        GroupData createdGroup = new GroupData().withName("test2'");
        app.group().fillForm(createdGroup);
        app.group().submitForm();
        app.group().returnToGroupPage();

        assertThat(app.group().count()).describedAs(COUNT_IS_DIFFERENT.r()).isEqualTo(before.size());

        Groups after = app.db().groups();
        assertThat(after).describedAs(COMPOSITION_IS_DIFFERENT.r()).isEqualTo(before);

        verifyGroupListInUI();
    }

}
