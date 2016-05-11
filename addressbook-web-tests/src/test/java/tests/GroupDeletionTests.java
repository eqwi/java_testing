package tests;

import data.GroupData;
import data.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    private void testDataCheckout() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("New group").withHeader("There is header").withFooter("There is footer"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups before = app.group().all();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        Assert.assertEquals(before.size() - 1, app.group().count());
        Groups after = app.group().all();

        assertThat(before.without(deletedGroup), equalTo(after));
    }
}
