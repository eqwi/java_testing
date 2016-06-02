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

    public void init() throws IOException {
        String propertyFile = System.getProperty("config", "web");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", propertyFile))));
        this.browser = properties.getProperty("test.browser");

        if (browser.equals("firefox")) wd = new FirefoxDriver();
        else if (browser.equals("chrome")) wd = new ChromeDriver();
        else if (browser.equals("ie")) wd = new InternetExplorerDriver();

        wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        wd.get(properties.getProperty("test.baseURL"));
    }

    public void stop() {
        wd.quit();
    }

}
