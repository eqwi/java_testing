package tests;

import data.GroupData;
import data.Groups;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData newGroup = new GroupData().withName("New group").withHeader("There is header").withFooter("There is footer");
        app.group().create(newGroup);
        assertThat(before.size() + 1, equalTo(app.group().count()));
        Groups after = app.group().all();

        assertThat(before.withAdded(newGroup.withId(after.stream().mapToInt((g) -> (g.getId())).max().getAsInt())), equalTo(after));
    }
}
