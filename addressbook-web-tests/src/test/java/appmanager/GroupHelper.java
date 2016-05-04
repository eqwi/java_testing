package appmanager;

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

    public void selectGroup(int id) {
        wd.findElements(By.name("selected[]")).get(id).click();
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
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData(id, name, null, null);
            groups.add(group);
        }
        return groups;
    }
}
