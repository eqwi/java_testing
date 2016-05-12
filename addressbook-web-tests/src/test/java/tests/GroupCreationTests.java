package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import data.GroupData;
import data.Groups;
import org.ietf.jgss.GSSContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.fail;

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "listOfGroups")
    public void testGroupCreation(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData newGroup = new GroupData().withName(group.getGroupName()).withHeader(group.getGroupHeader()).withFooter(group.getGroupFooter());
        app.group().create(newGroup);
        assertThat(before.size() + 1, equalTo(app.group().count()));
        Groups after = app.group().all();

        assertThat(before.withAdded(newGroup.withId(after.stream().mapToInt((g) -> (g.getId())).max().getAsInt())), equalTo(after));
    }

    @DataProvider
    public Iterator<Object[]> listOfGroups() throws IOException {
        BufferedReader reader;
        String format;

        /*if (new File("src/test/resources/GroupsData/groups.csv").exists()) {
            format = "csv";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/GroupsData/groups.csv")));
        } else*/ if (new File("src/test/resources/GroupsData/groups.json").exists()) {
            format = "json";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/GroupsData/groups.json")));
        }/* else if (new File("src/test/resources/GroupsData/groups.xml").exists()) {
            format = "xml";
            reader = new BufferedReader(new FileReader(new File("src/test/resources/GroupsData/groups.xml")));
        }*/ else throw new FileNotFoundException();

        String line = reader.readLine();
       /* if (format.equals("csv")) {
            List<Object[]> list = new ArrayList<>();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
                line = reader.readLine();
            }
            return list.iterator();
        }*/

        if (format.equals("json")) {
            String json = "";
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> list = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType());
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
            return list.stream().map((g) -> new Object[] {(g)}).collect(Collectors.toList()).iterator();
        }*/

        return null;
    }
}
