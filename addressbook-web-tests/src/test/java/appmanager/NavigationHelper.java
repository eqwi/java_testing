package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends BaseHelper {

    public NavigationHelper (WebDriver wd) {
        super(wd);
    }

    public void gotoHomePage() {
        clickButton(By.linkText("home page"));
    }

    public void gotoAddNewContactPage() {
        clickButton(By.linkText("add new"));
    }

    public void gotoGroupPage() {
        clickButton(By.linkText("groups"));
    }
}
