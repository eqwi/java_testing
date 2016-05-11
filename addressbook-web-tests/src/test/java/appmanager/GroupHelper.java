package appmanager;

import data.GroupData;
import data.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GroupHelper extends BaseHelper {

    private Groups groupCache = null;
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

    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
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

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void create(GroupData group) {
        clickCreateNewGroup();
        fillGroupForm(group);
        clickCreate();
        groupCache = null;
        navigationHelper.groupPage();
    }

    public void modify(GroupData modifyGroup) {
        selectGroupById(modifyGroup.getId());
        clickEditGroupButton();
        fillGroupForm(modifyGroup);
        submitGroupUpdate();
        groupCache = null;
        navigationHelper.groupPage();
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        clickDeleteButton();
        groupCache = null;
        navigationHelper.groupPage();
    }

    public Groups all() {
        if (groupCache != null) return new Groups(groupCache);
        groupCache = new Groups();
        List<WebElement> listOfNames = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : listOfNames) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groupCache.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupCache);
    }

}
