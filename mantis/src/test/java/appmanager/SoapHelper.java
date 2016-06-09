package appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import data.Issue;
import data.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("bt.bugTrackerURL")));
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("test.adminLogin"), app.getProperty("test.adminPassword"));
        return Arrays.asList(projects).stream().map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("bt.bugTrackerURL")));
        String[] categories = mc.mc_project_get_categories
                (app.getProperty("test.adminLogin"), app.getProperty("test.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(app.getProperty("test.adminLogin"), app.getProperty("test.adminPassword"), issueData);
        IssueData createdIssue = mc.mc_issue_get(app.getProperty("test.adminLogin"), app.getProperty("test.adminPassword"), issueId);
        return new Issue().withId(createdIssue.getId().intValue()).withSummary(createdIssue.getSummary()).withDescription(createdIssue.getDescription())
                .withProject(createdIssue.getProject().getId().intValue(), createdIssue.getProject().getName());
    }

    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("bt.bugTrackerURL")));
        IssueData issue = mc.mc_issue_get(app.getProperty("test.adminLogin"), app.getProperty("test.adminPassword"), BigInteger.valueOf(issueId));
        return new Issue().withId(issue.getId().intValue()).withDescription(issue.getDescription()).withSummary(issue.getSummary())
                .withProject(issue.getProject().getId().intValue(), issue.getProject().getName());
    }
}