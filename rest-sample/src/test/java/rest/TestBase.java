package rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class TestBase {

    private static final Properties properties = new Properties();

    @BeforeSuite
    public void init() throws IOException {
        String propertyFile = System.getProperty("config", "web");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", propertyFile))));
        RestAssured.authentication = RestAssured.basic(getProperty("bugify.key"), "");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private boolean isIssueOpen(int issueId) {
        String json = RestAssured.get("http://demo.bugify.com/api/issues/" + issueId + ".json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        Set<Issue> checkedIssues = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
        return !checkedIssues.iterator().next().getState_name().equals("Resolved")
                || !checkedIssues.iterator().next().getState_name().equals("Closed");
    }

    protected void skipIfNotFixed(int issueId) {
        if (!isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


    protected Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    protected int createIssue(Issue newIssue) throws IOException {
        String json = RestAssured.given().parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("http://demo.bugify.com/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
