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
                    .withCompany("New Company").withAddress("spb").withHomePhone("8915196819").withEmail("name.mid.surname@newcompany.ru"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withName("name11").withMidName("modified midname").withSurname("surname99")
                .withNickname("modified nickname").withTitle("modified title").withCompany("modified company").withAddress("modified address")
                .withHomePhone("11111111").withMobilePhone("1231313").withWorkPhone("1248283423").withEmail("modified.mail@mail.ru")
                .withEmail2("123123@fddsf").withEmail3("213@asasd.www");

        app.contact().modify(contact);
        Assert.assertEquals(before.size(), app.contact().count());
        Contacts after = app.contact().all();

        assertThat(before.without(modifiedContact).withAdded(contact), equalTo(after));
    }

}
