package cz.cvut.kbss.sformsmanager.model.dto;

public class ProjectStatsDTO {

    private int totalContexts;
    private int recordVersions;
    private int recordSnapshots;
    private int emptyRecordSnapshots;
    private int nonEmptyRecordSnapshots;
    private int records;
    private int formTemplates;
    private int formTemplateVersions;
    private int questionTemplates;
    private int questionTemplateSnapshots;
    private int answers;

    public ProjectStatsDTO(
            int totalContexts,
            int recordVersions,
            int recordSnapshots,
            int emptyRecordSnapshots,
            int nonEmptyRecordSnapshots,
            int records,
            int formTemplates,
            int formTemplateVersions,
            int questionTemplates,
            int questionTemplateSnapshots,
            int answers) {
        this.totalContexts = totalContexts;
        this.recordVersions = recordVersions;
        this.recordSnapshots = recordSnapshots;
        this.emptyRecordSnapshots = emptyRecordSnapshots;
        this.nonEmptyRecordSnapshots = nonEmptyRecordSnapshots;
        this.records = records;
        this.formTemplates = formTemplates;
        this.formTemplateVersions = formTemplateVersions;
        this.questionTemplates = questionTemplates;
        this.questionTemplateSnapshots = questionTemplateSnapshots;
        this.answers = answers;
    }

    public int getTotalContexts() {
        return totalContexts;
    }

    public void setTotalContexts(int totalContexts) {
        this.totalContexts = totalContexts;
    }

    public int getRecordVersions() {
        return recordVersions;
    }

    public void setRecordVersions(int recordVersions) {
        this.recordVersions = recordVersions;
    }

    public int getRecordSnapshots() {
        return recordSnapshots;
    }

    public void setRecordSnapshots(int recordSnapshots) {
        this.recordSnapshots = recordSnapshots;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getFormTemplates() {
        return formTemplates;
    }

    public void setFormTemplates(int formTemplates) {
        this.formTemplates = formTemplates;
    }

    public int getFormTemplateVersions() {
        return formTemplateVersions;
    }

    public void setFormTemplateVersions(int formTemplateVersions) {
        this.formTemplateVersions = formTemplateVersions;
    }

    public int getQuestionTemplates() {
        return questionTemplates;
    }

    public void setQuestionTemplates(int questionTemplates) {
        this.questionTemplates = questionTemplates;
    }

    public int getQuestionTemplateSnapshots() {
        return questionTemplateSnapshots;
    }

    public void setQuestionTemplateSnapshots(int questionTemplateSnapshots) {
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getEmptyRecordSnapshots() {
        return emptyRecordSnapshots;
    }

    public void setEmptyRecordSnapshots(int emptyRecordSnapshots) {
        this.emptyRecordSnapshots = emptyRecordSnapshots;
    }

    public int getNonEmptyRecordSnapshots() {
        return nonEmptyRecordSnapshots;
    }

    public void setNonEmptyRecordSnapshots(int nonEmptyRecordSnapshots) {
        this.nonEmptyRecordSnapshots = nonEmptyRecordSnapshots;
    }
}
