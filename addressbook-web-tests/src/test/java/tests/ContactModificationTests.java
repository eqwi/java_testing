package tests;

import data.ContactData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getSessionHelper().isElementPresent(By.cssSelector("img[alt=\"Edit\"]"))) {
            app.getContactHelper().createContact(new ContactData("name", "mid", "surname", "vahvah", "director", "New Company", "spb", "8915196819", "name.mid.surname@newcompany.ru"));
        }
        app.getContactHelper().clickModifyContactButton();
        app.getContactHelper().fillContactForm(new ContactData("name", "modified midname", "surname",
                                                                "modified nickname", "modified title", "modified company",
                                                                "modified address", "11111111", "modified.mail@mail.ru"));
        app.getContactHelper().clickUpdateButton();
        app.getNavigationHelper().gotoHomePage();
    }
}
