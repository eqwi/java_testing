package tests;

import data.ContactData;
import data.Contacts;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    private void testDataCheckout() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withName("name").withMidName("mid").withSurname("surname").withNickname("vahvah").withTitle("director")
                    .withCompany("New Company").withAddress("spb").withPhone("8915196819").withEmail("name.mid.surname@newcompany.ru"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withName("name11").withMidName("modified midname").withSurname("surname99")
                .withNickname("modified nickname").withTitle("modified title").withCompany("modified company").withAddress("modified address")
                .withPhone("11111111").withEmail("modified.mail@mail.ru");

        app.contact().modify(contact);
        Contacts after = app.contact().all();
        Assert.assertEquals(before.size(), after.size());

        assertThat(before.without(modifiedContact).withAdded(contact), equalTo(after));
    }

}
