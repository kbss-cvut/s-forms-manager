package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Date;

public class RecordSnapshotDTO {

    public final String recordSnapshotURI;
    public final String internalKey;
    public final String formTemplateVersionKey;
    public final String formTemplateVersionInternalName;
    public final Date recordSnapshotCreated;
    public final String remoteSampleContextURI;
    public final int numberOfAnswers;

    public RecordSnapshotDTO(String recordSnapshotURI, String internalKey, String formTemplateVersionKey, String formTemplateVersionInternalName, Date recordSnapshotCreated, String remoteSampleContextURI, int numberOfAnswers) {
        this.recordSnapshotURI = recordSnapshotURI;
        this.internalKey = internalKey;
        this.formTemplateVersionKey = formTemplateVersionKey;
        this.formTemplateVersionInternalName = formTemplateVersionInternalName;
        this.recordSnapshotCreated = recordSnapshotCreated;
        this.remoteSampleContextURI = remoteSampleContextURI;
        this.numberOfAnswers = numberOfAnswers;
    }

    public String getRecordSnapshotURI() {
        return recordSnapshotURI;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public String getFormTemplateVersionKey() {
        return formTemplateVersionKey;
    }

    public String getFormTemplateVersionInternalName() {
        return formTemplateVersionInternalName;
    }

    public Date getRecordSnapshotCreated() {
        return recordSnapshotCreated;
    }

    public String getRemoteSampleContextURI() {
        return remoteSampleContextURI;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }
}
