package tests;

import data.GroupData;
import data.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    private void testDataCheckout() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("New group").withHeader("There is header").withFooter("There is footer"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("new group name").withHeader("There is header").withFooter(null);
        app.goTo().groupPage();
        app.group().modify(group);
        Groups after = app.db().groups();

        assertThat(before.without(modifiedGroup).withAdded(group), equalTo(after));
    }

}
