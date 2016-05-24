package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private static final Properties properties = new Properties();
    private WebDriver wd;
    private String browser;

    private ContactHelper contactHelper;
    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private DbHelper dbHelper;

    public void init() throws IOException {
        String propertyFile = System.getProperty("config", "web");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", propertyFile))));
        this.browser = properties.getProperty("test.browser");

        dbHelper = new DbHelper();

        if (browser.equals("firefox")) wd = new FirefoxDriver();
        else if (browser.equals("chrome")) wd = new ChromeDriver();
        else if (browser.equals("ie")) wd = new InternetExplorerDriver();

        wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        wd.get(properties.getProperty("test.baseURL"));

        contactHelper = new ContactHelper(wd);
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);

        sessionHelper.login(properties.getProperty("test.adminLogin"), properties.getProperty("test.adminPassword"));
    }

    public void stop() {
        wd.quit();
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }
}
