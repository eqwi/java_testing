package tests;

import data.GroupData;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.gotoGroupPage();
        app.clickCreateNewGroup();
        app.fillGroupForm(new GroupData("New group", "There is header", "There is footer"));
        app.clickCreate();
        app.gotoGroupPage();
    }

}
