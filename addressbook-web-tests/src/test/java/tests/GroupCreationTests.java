package tests;

import data.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData newGroup = new GroupData("New group", "There is header", "There is footer");
        app.getGroupHelper().createGroup(newGroup);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(before.size() + 1, after.size());

        before.add(newGroup);
        Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
