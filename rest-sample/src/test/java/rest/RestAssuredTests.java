package rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;


public class RestAssuredTests extends TestBase {

    @Test
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(13);
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("test issue").withDescription("new test issue").withState_name("Open");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        Assert.assertEquals(newIssues, oldIssues);
    }

}
