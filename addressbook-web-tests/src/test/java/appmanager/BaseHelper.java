package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseHelper {
    private FirefoxDriver wd;

    public BaseHelper(FirefoxDriver wd) {
        this.wd = wd;
    }

    protected void clickButton(By locator) {
        wd.findElement(locator).click();
    }

    protected void fillField(By locator, String text) {
        clickButton(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
