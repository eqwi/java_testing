package tests;

import data.ContactData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        if (!app.getSessionHelper().isElementPresent(By.name("selected[]"))) {
            app.getContactHelper().createContact(new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().clickDeleteButton();
        app.getContactHelper().acceptDeletion();
    }
}
