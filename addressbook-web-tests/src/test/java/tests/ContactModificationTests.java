package tests;

import data.ContactData;
import org.testng.annotations.Test;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getContactHelper().clickModifyContactButton();
        app.getContactHelper().fillContactForm(new ContactData("modified name", "modified midname", "modified surname",
                                                                "modified nickname", "modified title", "modified company",
                                                                "modified address", "11111111", "modified.mail@mail.ru"));
        app.getContactHelper().clickUpdateButton();
        app.getNavigationHelper().gotoHomePage();
    }
}
