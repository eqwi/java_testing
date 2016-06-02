package appmanager;

import data.UserInfo;
import org.openqa.selenium.By;

import java.util.Random;

public class RegistrationHelper extends BaseHelper {

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("test.baseURL") + "signup_page.php");
        fillField(By.name("username"), username);
        fillField(By.name("email"), email);
        clickButton(By.xpath("//input[@value='Signup']"));
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        fillField(By.name("password"), password);
        fillField(By.name("password_confirm"), password);
        clickButton(By.xpath("//input[@value='Update User']"));
    }

    public UserInfo resetPassword() {
        UserInfo user = new UserInfo();
        wd.get(app.getProperty("test.baseURL") + "login_page.php");
        fillField(By.name("username"), app.getProperty("test.adminLogin"));
        fillField(By.name("password"), app.getProperty("test.adminPassword"));
        clickButton(By.xpath("//input[@value='Login']"));
        clickButton(By.linkText("Manage Users"));
        // Собираем инфу о юзерах на странице и выбираем любого из них
        int count = wd.findElements(By.xpath("//table[@cellspacing='1']/tbody/tr")).size() - 3; // на 3 меньше потому что шапка и строка в конце тоже будут включены
        // Собираем инфу о выбранном пользователе, чтобы это был не админ
        while (true) {
            // Не самый оптимальный способ, но решил попробовать
            int id = getRandomInt(1, count);
            if (wd.findElement(By.xpath("//table[@cellspacing='1']/tbody/tr[" + (id + 2) +"]/td[4]")).getText().equals("administrator")) continue;
            // Если не админ то все норм и продолжаем
            user.setUserName(wd.findElement(By.xpath("//table[@cellspacing='1']/tbody/tr[" + (id + 2) +"]/td[1]/a")).getText());
            user.setMail(wd.findElement(By.xpath("//table[@cellspacing='1']/tbody/tr[" + (id + 2) +"]/td[3]")).getText());
            // Собрали инфу, теперь сбрасываем ему пароль
            clickButton(By.xpath("//table[@cellspacing='1']/tbody/tr[" + (id + 2) +"]/td[1]/a"));
            clickButton(By.xpath("//input[@value='Reset Password']"));
            break;
        }
        return user;
    }

    public void changePassword(String confirmationLink, String newPassword) {
        wd.get(confirmationLink);
        fillField(By.name("password"), newPassword);
        fillField(By.name("password_confirm"), newPassword);
        clickButton(By.xpath("//input[@value='Update User']"));
    }

    // Для взятия случайного числа из интервала
    public int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min)+min;
    }
}
