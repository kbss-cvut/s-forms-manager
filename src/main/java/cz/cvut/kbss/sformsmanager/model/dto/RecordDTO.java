package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Date;

public class RecordDTO {

    public final String recordURI;
    public final Date recordCreated;
    public final String internalKey;
    public final String remoteSampleContextURI;
    public final Integer numberOfRecordSnapshots;
    public final Integer numberOfRecordVersions;

    public RecordDTO(String recordURI, Date recordCreated, String internalKey, String remoteSampleContextURI, int numberOfRecordSnapshots, int numberOfRecordVersions) {
        this.recordURI = recordURI;
        this.recordCreated = recordCreated;
        this.internalKey = internalKey;
        this.remoteSampleContextURI = remoteSampleContextURI;
        this.numberOfRecordSnapshots = numberOfRecordSnapshots;
        this.numberOfRecordVersions = numberOfRecordVersions;
    }

    public RecordDTO(String recordURI, Date recordCreated, String internalKey, String remoteSampleContextURI) {
        this.recordURI = recordURI;
        this.recordCreated = recordCreated;
        this.internalKey = internalKey;
        this.remoteSampleContextURI = remoteSampleContextURI;
        this.numberOfRecordSnapshots = 0;
        this.numberOfRecordVersions = 0;
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

    public int getNumberOfRecordVersions() {
        return numberOfRecordVersions;
    }

    public String getRemoteSampleContextURI() {
        return remoteSampleContextURI;
    }
}
