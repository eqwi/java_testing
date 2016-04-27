package appmanager;

import data.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GroupHelper extends BaseHelper {

    public GroupHelper (WebDriver wd) {
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

    public void selectGroup() {
        clickButton(By.name("selected[]"));
    }

    public void clickEditGroupButton() {
        clickButton(By.name("edit"));
    }

    public void submitGroupUpdate() {
        clickButton(By.name("update"));
    }

    public void clickDeleteButton() {
        clickButton(By.name("delete"));
    }
}
