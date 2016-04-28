package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends BaseHelper {

    public NavigationHelper (WebDriver wd) {
        super(wd);
    }

    public void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))) return;

        clickButton(By.linkText("home page"));
    }

    public void gotoAddNewContactPage() {
        String textOnPage = wd.findElement(By.tagName("h1")).getAttribute("value");
        if ("Edit / add address book entry".equals(textOnPage)) return;

        clickButton(By.linkText("add new"));
    }

    public void gotoGroupPage() {
        String textOnPage = wd.findElement(By.tagName("h1")).getAttribute("value");
        if ("Groups".equals(textOnPage)) return;

        clickButton(By.linkText("groups"));
    }
}
