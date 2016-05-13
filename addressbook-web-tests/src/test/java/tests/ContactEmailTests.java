package tests;

import data.ContactData;
import data.Contacts;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    @Test
    public void testContactEmailsOnDifferentPages() {
        Contacts contactsList = app.contact().all();
        ContactData checkingContact = contactsList.iterator().next();
        app.contact().clickModifyContactButtonOfChosenContact(checkingContact.getId());
        ContactData checkingContactEditPage = app.contact().collectContactDataFromEditPage();
        app.goTo().homePage();

        assertThat(checkingContact.getAllEmails(), equalTo(mergeEmails(checkingContactEditPage)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
    }

}
