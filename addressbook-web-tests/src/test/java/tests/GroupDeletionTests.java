package tests;

import data.GroupData;
import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupExist()) {
            app.getGroupHelper().createGroup(new GroupData("New group", "There is header", "There is footer"));
        }
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickDeleteButton();
        app.getNavigationHelper().gotoGroupPage();
    }
}
