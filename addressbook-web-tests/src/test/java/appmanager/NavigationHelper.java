package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends BaseHelper {

    public NavigationHelper (FirefoxDriver wd) {
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
