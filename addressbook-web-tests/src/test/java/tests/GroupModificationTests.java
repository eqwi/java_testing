package tests;

import data.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupExist()) {
            app.getGroupHelper().createGroup(new GroupData("New group", "There is header", "There is footer"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().clickEditGroupButton();
        GroupData modifyGroup = new GroupData(before.get(before.size() - 1).getId(), "new group name", "There is header", null);
        app.getGroupHelper().fillGroupForm(modifyGroup);
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(before.size(), after.size());

        before.remove(before.size() - 1);
        before.add(modifyGroup);
        Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
