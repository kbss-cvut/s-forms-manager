package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.RecordVersion)
public class RecordVersion extends LocalEntity implements Serializable, HasConnection {

    public static final String INITIAL_RECORD_HASH_KEY = "0";

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_hasRecord, fetch = FetchType.LAZY)
    private Record hasRecord;

    // key is the  QUESTION + ANSWER-VALUES + RECORD-URI -> hash

    @Transient
    private String connectionName; // used for creating sparql descriptor

    public RecordVersion() {
    }

    public RecordVersion(String recordQuestionAnswersHashKey, Record hasRecord, String connectionName) {
        super(recordQuestionAnswersHashKey);
        this.hasRecord = hasRecord;
        this.connectionName = connectionName;
    }

    public Record getHasRecord() {
        return hasRecord;
    }

    public void setHasRecord(Record hasRecord) {
        this.hasRecord = hasRecord;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}

