package cz.cvut.kbss.sformsmanager.model.request;

public class CreateTicketRequest {
    private String projectName;
    private String recordContextUri;
    private String name;
    private String description;
    private boolean relateToForm;
    private boolean relateToFormVersion;
    private boolean relateToQuestion;
    private String questionOriginPath;

    public CreateTicketRequest() {
    }

    @Override
    public String toString() {
        return "CreateTicketRequest{" +
                "projectName='" + projectName + '\'' +
                ", recordContextUri='" + recordContextUri + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", relateToForm=" + relateToForm +
                ", relateToFormVersion=" + relateToFormVersion +
                ", relateToQuestion=" + relateToQuestion +
                ", questionOriginPath='" + questionOriginPath + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRelateToForm() {
        return relateToForm;
    }

    public void setRelateToForm(boolean relateToForm) {
        this.relateToForm = relateToForm;
    }

    public boolean isRelateToFormVersion() {
        return relateToFormVersion;
    }

    public void setRelateToFormVersion(boolean relateToFormVersion) {
        this.relateToFormVersion = relateToFormVersion;
    }

    public boolean isRelateToQuestion() {
        return relateToQuestion;
    }

    public void setRelateToQuestion(boolean relateToQuestion) {
        this.relateToQuestion = relateToQuestion;
    }

    public String getQuestionOriginPath() {
        return questionOriginPath;
    }

    public void setQuestionOriginPath(String questionOriginPath) {
        this.questionOriginPath = questionOriginPath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRecordContextUri() {
        return recordContextUri;
    }

    public void setRecordContextUri(String recordContextUri) {
        this.recordContextUri = recordContextUri;
    }
}