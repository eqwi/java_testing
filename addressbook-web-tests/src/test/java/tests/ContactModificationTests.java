package tests;

import data.ContactData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getSessionHelper().isElementPresent(By.cssSelector("img[alt=\"Edit\"]"))) {
            app.getContactHelper().createContact(new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().clickModifyContactButton(before.size() - 1);
        ContactData modifyContact = new ContactData(before.get(before.size() - 1).getId(), "name11", "modified midname", "surname99",
                                                    "modified nickname", "modified title", "modified company",
                                                    "modified address", "11111111", "modified.mail@mail.ru");
        app.getContactHelper().fillContactForm(modifyContact);
        app.getContactHelper().clickUpdateButton();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());

        before.remove(before.size() - 1);
        before.add(modifyContact);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
