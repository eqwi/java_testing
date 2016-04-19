package tests;

import data.GroupData;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().clickCreateNewGroup();
        app.getGroupHelper().fillGroupForm(new GroupData("New group", "There is header", "There is footer"));
        app.getGroupHelper().clickCreate();
        app.getNavigationHelper().gotoGroupPage();
    }

}
