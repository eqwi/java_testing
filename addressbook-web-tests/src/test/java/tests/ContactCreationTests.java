package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.ContactData;
import data.Contacts;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test(dataProvider = "listOfContacts")
    public void testContactCreation(ContactData contact) {
        Contacts before = app.contact().all();
        ContactData newContact = new ContactData()
                .withName(contact.getName()).withMidName(contact.getMidName()).withSurname(contact.getSurname()).withNickname(contact.getNickname())
                .withTitle(contact.getTitle()).withCompany(contact.getCompany()).withAddress(contact.getAddress()).withHomePhone(contact.getHomePhone())
                .withMobilePhone(contact.getMobilePhone()).withWorkPhone(contact.getWorkPhone()).withEmail(contact.getEmail()).withEmail2(contact.getEmail2())
                .withEmail3(contact.getEmail3());
        app.contact().create(newContact);
        Assert.assertEquals(before.size() + 1, app.contact().count());
        Contacts after = app.contact().all();

        assertThat(before.withAdded(newContact.withId(after.stream().mapToInt((c) -> (c.getId())).max().getAsInt())), equalTo(after));
    }

    @DataProvider
    public Iterator<Object[]> listOfContacts() throws IOException {
        BufferedReader reader;
        String format;

        /*if (new File("src/test/resources/ContactsData/contacts.csv").exists()) {
            format = "csv";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/ContactsData/groups.csv")));
        } else*/ if (new File("src/test/resources/ContactsData/contacts.json").exists()) {
            format = "json";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/ContactsData/contacts.json")));
        }/* else if (new File("src/test/resources/ContactsData/contacts.xml").exists()) {
            format = "xml";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/ContactsData/contacts.xml")));
        }*/ else throw new FileNotFoundException();

        String line = reader.readLine();
       /* if (format.equals("csv")) {
            List<Object[]> list = new ArrayList<>();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
                line = reader.readLine();
            }
            reader.close();
            return list.iterator();
        }*/

        if (format.equals("json")) {
            String json = "";
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> list = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
            reader.close();
            return list.stream().map((g) -> new Object[]{(g)}).collect(Collectors.toList()).iterator();
        }

        /*if (format.equals("xml")) {
            String xml = "";
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(GroupData.class);
            List<GroupData> list = (List<GroupData>) xStream.fromXML(xml);
            reader.close();
            return list.stream().map((g) -> new Object[] {(g)}).collect(Collectors.toList()).iterator();
        }*/

        return null;
    }

}
