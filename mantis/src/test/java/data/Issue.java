package data;

public class Issue {

    private int id;
    private String description;
    private String summary;
    private Project project;

    public int getId() {
        return id;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Issue withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Issue withProject(Project project) {
        this.project = project;
        return this;
    }

    public Issue withProject(int id, String name) {
        if (project == null) this.project = new Project().withId(id).withName(name);
        else this.project = project.withName(name).withId(id);
        return this;
    }
}
