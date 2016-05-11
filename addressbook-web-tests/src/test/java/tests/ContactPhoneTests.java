package tests;

import data.ContactData;
import data.Contacts;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @Test
    public void testContactPhonesOnDifferentPages() {
        Contacts contactsList = app.contact().all();
        ContactData checkingContact = contactsList.iterator().next();
        app.contact().clickModifyContactButtonOfChosenContact(checkingContact.getId());
        ContactData checkingContactEditPage = app.contact().collectContactDataFromEditPage();

        assertThat(checkingContact.getAllPhones(), equalTo(mergePhones(checkingContactEditPage)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals("")).map(ContactPhoneTests::cleaned).collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}
