package generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import data.GroupData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
    @Parameter(names = "-c", description = "Group count to generate")
    private int count = 0;

    @Parameter(names = "-f", description = "(OPTIONAL)File format. Supported file formats: .csv, .xml, .json. By default - .json")
    private String fileFormat = ".json";

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroups(count);
        if (fileFormat.equals(".csv")) saveAsCSV(groups);
        else if (fileFormat.equals(".json")) saveAsJSON(groups);
        else if (fileFormat.equals(".xml")) saveAsXML(groups);
        else System.out.println("Unrecognized file format: " + fileFormat);
    }

    private void saveAsCSV(List<GroupData> groups) throws IOException {
        Writer writer = new FileWriter("src/test/resources/GroupsData/groups.csv");
        for (GroupData group : groups) writer.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getGroupHeader(), group.getGroupFooter()));
        writer.close();
    }

    private void saveAsJSON(List<GroupData> groups) throws IOException {
        Writer writer = new FileWriter("src/test/resources/GroupsData/groups.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        writer.write(json);
        writer.close();
    }

    private void saveAsXML(List<GroupData> groups) throws IOException {
        Writer writer = new FileWriter("src/test/resources/GroupsData/groups.xml");
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
        writer.write(xml);
        writer.close();
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) groups.add(new GroupData().withName("name" + (i+1)).withHeader("header" + (i+1)).withFooter("footer" + (i+1)));
        return groups;
    }
}
