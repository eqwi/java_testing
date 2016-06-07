package tests;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import data.Issue;
import data.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        skipIfNotFixed(1);
        Set<Project> projects = app.soap().getProjects();
        MantisConnectPortType mc = new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("bt.bugTrackerURL")));
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(1);
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test Issue").withDescription("Test Issue Description").withProject(projects.iterator().next());
        Issue createdIssue = app.soap().addIssue(issue);
        Assert.assertEquals(issue.getSummary(), createdIssue.getSummary());
    }
}
