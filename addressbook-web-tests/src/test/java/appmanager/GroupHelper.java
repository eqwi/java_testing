package appmanager;

import data.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupHelper extends BaseHelper {

    public GroupHelper (FirefoxDriver wd) {
        super(wd);
    }

    public void clickCreate() {
        clickButton(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        fillField(By.name("group_name"), groupData.getGroupName());
        fillField(By.name("group_header"), groupData.getGroupHeader());
        fillField(By.name("group_footer"), groupData.getGroupFooter());
    }

    public void clickCreateNewGroup() {
        clickButton(By.name("new"));
    }

}
