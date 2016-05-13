package tests;

import data.ContactData;
import data.Contacts;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailedInfoTests extends TestBase {

    @Test
    public void testContactDetailedInfoPage() {
        Contacts contactsList = app.contact().all();
        ContactData checkingContact = contactsList.iterator().next();
        app.contact().clickContactDetailedInfoButtonOfChosenContact(checkingContact.getId());
        ContactData checkingContactDetailedData = app.contact().collectContactDataFromDetailedInfoPage();
        app.goTo().homePage();
        app.contact().clickModifyContactButtonOfChosenContact(checkingContact.getId());
        checkingContact = app.contact().collectContactDataFromEditPage();
        app.goTo().homePage();

        assertThat(mergeName(checkingContact), equalTo(checkingContactDetailedData.getFullName()));
        assertThat(checkingContact.getNickname(), equalTo(checkingContactDetailedData.getNickname()));
        assertThat(checkingContact.getTitle(), equalTo(checkingContactDetailedData.getTitle()));
        assertThat(checkingContact.getCompany(), equalTo(checkingContactDetailedData.getCompany()));
        assertThat(checkingContact.getAddress(), equalTo(checkingContactDetailedData.getAddress()));
        assertThat(checkingContact.getHomePhone(), equalTo(checkingContactDetailedData.getHomePhone()));
        assertThat(checkingContact.getMobilePhone(), equalTo(checkingContactDetailedData.getMobilePhone()));
        assertThat(checkingContact.getWorkPhone(), equalTo(checkingContactDetailedData.getWorkPhone()));
        assertThat(checkingContact.getEmail(), equalTo(checkingContactDetailedData.getEmail()));
        assertThat(checkingContact.getEmail2(), equalTo(checkingContactDetailedData.getEmail2()));
        assertThat(checkingContact.getEmail3(), equalTo(checkingContactDetailedData.getEmail3()));
    }

    private String mergeName(ContactData contact) {
        return Arrays.asList(contact.getName(), contact.getMidName(), contact.getSurname())
                .stream().filter((s) -> ! s.equals("")).collect(Collectors.joining(" "));
    }

}
