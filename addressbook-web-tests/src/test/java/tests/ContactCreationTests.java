package tests;

import data.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData newContact = new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru");
        app.getContactHelper().createContact(newContact);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size() + 1, after.size());

        before.add(newContact);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
