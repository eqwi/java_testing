package tests;

import data.GroupData;
import data.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    private void testDataCheckout() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("New group").withHeader("There is header").withFooter("There is footer"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("new group name").withHeader("There is header").withFooter(null);
        app.group().modify(group);
        Groups after = app.group().all();
        Assert.assertEquals(before.size(), after.size());

        assertThat(before.without(modifiedGroup).withAdded(group), equalTo(after));
    }

}
