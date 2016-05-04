package tests;

import data.ContactData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        if (!app.getSessionHelper().isElementPresent(By.name("selected[]"))) {
            app.getContactHelper().createContact(new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().clickDeleteButton();
        app.getContactHelper().acceptDeletion();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size() - 1, after.size());

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
