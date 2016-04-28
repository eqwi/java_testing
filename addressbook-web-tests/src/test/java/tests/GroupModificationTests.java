package tests;

import data.GroupData;
import org.testng.annotations.Test;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupExist()) {
            app.getGroupHelper().createGroup(new GroupData("New group", "There is header", "There is footer"));
        }
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickEditGroupButton();
        app.getGroupHelper().fillGroupForm(new GroupData("new group name", "There is header", null));
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().gotoGroupPage();
    }
}
