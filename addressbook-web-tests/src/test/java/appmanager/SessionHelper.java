package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper extends BaseHelper {

    public SessionHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void login(String username, String password) {
        fillField(By.name("user"), username);
        fillField(By.name("pass"), password);
        clickButton(By.xpath("//form[@id='LoginForm']/input[3]"));
    }
}
