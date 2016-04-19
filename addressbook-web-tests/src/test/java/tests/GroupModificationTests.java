package tests;

import data.GroupData;
import org.testng.annotations.Test;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickEditGroupButton();
        app.getGroupHelper().fillGroupForm(new GroupData("new group name", "new group header", "new group footer"));
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().gotoGroupPage();
    }
}
