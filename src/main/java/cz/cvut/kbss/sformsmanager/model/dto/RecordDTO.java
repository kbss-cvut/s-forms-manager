package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Date;

public class RecordDTO {

    public final String recordURI;
    public final Date recordCreated;
    public final String internalKey;
    public final String remoteSampleContextURI;
    public final int numberOfRecordSnapshots;
    public final int numberOfVersions;

    public RecordDTO(String recordURI, Date recordCreated, String internalKey, String remoteSampleContextURI, int numberOfRecordSnapshots, int numberOfVersions) {
        this.recordURI = recordURI;
        this.recordCreated = recordCreated;
        this.internalKey = internalKey;
        this.remoteSampleContextURI = remoteSampleContextURI;
        this.numberOfRecordSnapshots = numberOfRecordSnapshots;
        this.numberOfVersions = numberOfVersions;
    }

    public String getRecordURI() {
        return recordURI;
    }

    public Date getRecordCreated() {
        return recordCreated;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public int getNumberOfRecordSnapshots() {
        return numberOfRecordSnapshots;
    }

    public int getNumberOfVersions() {
        return numberOfVersions;
    }

    public String getRemoteSampleContextURI() {
        return remoteSampleContextURI;
    }
}
