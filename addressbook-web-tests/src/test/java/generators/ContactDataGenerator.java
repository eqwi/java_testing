package generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import data.ContactData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Count of contacts to generate")
    private int count = 0;

    @Parameter(names = "-f", description = "(OPTIONAL)File format. Supported file formats: .csv, .xml, .json. By default - .json")
    private String fileFormat = ".json";

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (fileFormat.equals(".csv")) saveAsCSV(contacts);
        else if (fileFormat.equals(".xml")) saveAsXML(contacts);
        else if (fileFormat.equals(".json")) saveAsJson(contacts);
        else System.out.println("Unrecognized file format: " + fileFormat);
    }

    private void saveAsJson(List<ContactData> contacts) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter("src/test/resources/ContactsData/contacts.json")) {
            writer.write(json);
        }
    }

    private void saveAsXML(List<ContactData> contacts) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        try (Writer writer = new FileWriter("src/test/resources/ContactsData/contacts.xml")) {
            writer.write(xml);
        }
    }

    private void saveAsCSV(List<ContactData> contacts) throws IOException {
        try (Writer writer = new FileWriter("src/test/resources/ContactsData/contacts.csv")) {
            for (ContactData contact : contacts)
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                        contact.getName(), contact.getMidName(), contact.getSurname(), contact.getNickname(), contact.getTitle(), contact.getCompany(),
                        contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(),
                        contact.getEmail(), contact.getEmail2(), contact.getEmail3()));
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) contacts.add(new ContactData()
                .withName("name" + (i+1)).withSurname("surname" + (i+1)).withMidName("mid" + (i+1)).withNickname("vahvah" + (i+1))
                .withAddress("address" + (i+1)).withCompany("Company" + (i+1)).withTitle("title")
                .withHomePhone("21345" + (i+1)).withMobilePhone("123" + (i+1)).withWorkPhone("6785834" + (i+1))
                .withEmail("name.mid.surname@" + (i+1)).withEmail2("name@" + (i+1)).withEmail3("surname@" + (i+1)));
        return contacts;
    }
}
