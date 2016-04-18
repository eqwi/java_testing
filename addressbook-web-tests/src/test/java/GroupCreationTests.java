import appmanager.TestBase;
import data.GroupData;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        gotoGroupPage();
        clickCreateNewGroup();
        fillGroupForm(new GroupData("New group", "There is header", "There is footer"));
        clickCreate();
        gotoGroupPage();
    }

}
