package appmanager;

import data.ContactData;
import data.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends BaseHelper {

    private NavigationHelper navigationHelper = new NavigationHelper(wd);

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

    public void createGroup(GroupData group) {
        clickCreateNewGroup();
        fillGroupForm(group);
        clickCreate();
        navigationHelper.gotoGroupPage();
    }

    public boolean isGroupExist() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<GroupData> getGroupList() {
        List<GroupData> groups = new ArrayList<>();
        List<WebElement> listOfNames = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : listOfNames) {
            String name = element.getText();
            GroupData group = new GroupData(name, null, null);
            groups.add(group);
        }
        return groups;
    }
}
