package tests;

import data.MailMessage;
import data.UserInfo;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void resetPassword() throws IOException, MessagingException {
        String newPassword = "pass1234";
        UserInfo user = app.registration().resetPassword();
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, user.mail());
        app.registration().changePassword(confirmationLink, newPassword);
        assertTrue(app.newSession().login(user.userName(), newPassword));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String mail) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.receiver.equals(mail)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
