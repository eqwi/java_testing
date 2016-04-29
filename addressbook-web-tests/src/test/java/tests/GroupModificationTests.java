package tests;

import data.ContactData;
import data.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupExist()) {
            app.getGroupHelper().createGroup(new GroupData("New group", "There is header", "There is footer"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickEditGroupButton();
        app.getGroupHelper().fillGroupForm(new GroupData("new group name", "There is header", null));
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().gotoGroupPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());
    }
}
