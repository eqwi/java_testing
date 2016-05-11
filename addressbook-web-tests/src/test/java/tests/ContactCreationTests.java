package tests;

import data.ContactData;
import data.Contacts;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        Contacts before = app.contact().all();
        ContactData newContact = new ContactData()
                .withName("name").withMidName("mid").withSurname("surname").withNickname("vahvah").withTitle("director")
                .withCompany("New Company").withAddress("spb").withPhone("8915196819").withEmail("name.mid.surname@newcompany.ru");
        app.contact().create(newContact);
        Contacts after = app.contact().all();
        Assert.assertEquals(before.size() + 1, after.size());

        assertThat(before.withAdded(newContact.withId(after.stream().mapToInt((c) -> (c.getId())).max().getAsInt())), equalTo(after));
    }

}
