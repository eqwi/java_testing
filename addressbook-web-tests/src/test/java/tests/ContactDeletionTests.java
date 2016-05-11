package tests;

import data.ContactData;
import data.Contacts;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    private void testDataCheckout() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withName("name").withMidName("mid").withSurname("surname").withNickname("vahvah").withTitle("director")
                    .withCompany("New Company").withAddress("spb").withHomePhone("8915196819").withEmail("name.mid.surname@newcompany.ru"));
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        Assert.assertEquals(before.size() - 1, app.contact().count());
        Contacts after = app.contact().all();

        assertThat(before.without(deletedContact), equalTo(after));
    }

}
