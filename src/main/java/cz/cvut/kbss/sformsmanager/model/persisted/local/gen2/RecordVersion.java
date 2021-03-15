package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.RecordVersion)
public class RecordVersion extends LocalEntity implements Serializable {

    public static final String INITIAL_RECORD_HASH_KEY = "0";

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_hasRecord, fetch = FetchType.LAZY)
    private Record hasRecord;

    // key is the  QUESTION + ANSWER-VALUES + RECORD-URI -> hash

    public RecordVersion() {
    }

    public RecordVersion(String recordQuestionAnswersHashKey, Record hasRecord) {
        super(recordQuestionAnswersHashKey);
        this.hasRecord = hasRecord;
    }

    public Record getHasRecord() {
        return hasRecord;
    }

    public void setHasRecord(Record hasRecord) {
        this.hasRecord = hasRecord;
    }
}

