package appmanager;

import data.GroupData;
import data.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void create(GroupData group) {
        clickCreateNewGroup();
        fillGroupForm(group);
        clickCreate();
        navigationHelper.groupPage();
    }

    public void modify(GroupData modifyGroup) {
        selectGroupById(modifyGroup.getId());
        clickEditGroupButton();
        fillGroupForm(modifyGroup);
        submitGroupUpdate();
        navigationHelper.groupPage();
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        clickDeleteButton();
        navigationHelper.groupPage();
    }

    public Groups all() {
        Groups groups = new Groups();
        List<WebElement> listOfNames = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : listOfNames) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groups.add(new GroupData().withId(id).withName(name));
        }
        return groups;
    }

}
