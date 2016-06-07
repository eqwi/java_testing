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
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private SoapHelper soapHelper;

    public void init() throws IOException {
        String propertyFile = System.getProperty("config", "web");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", propertyFile))));
        this.browser = properties.getProperty("test.browser");
    }

    public void stop() {
        if (wd != null) wd.quit();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public FtpHelper ftp() {
        if (ftp == null) ftp = new FtpHelper(this);
        return ftp;
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) registrationHelper = new RegistrationHelper(this);
        return registrationHelper;
    }

    public WebDriver getDriver() {

        if (browser.equals("firefox")) wd = new FirefoxDriver();
        else if (browser.equals("chrome")) wd = new ChromeDriver();
        else if (browser.equals("ie")) wd = new InternetExplorerDriver();

        wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        return wd;
    }

    public MailHelper mail() {
        if (mailHelper == null) mailHelper = new MailHelper(this);
        return mailHelper;
    }

    public SoapHelper soap() {
        if (soapHelper == null) soapHelper = new SoapHelper(this);
        return soapHelper;
    }
}
