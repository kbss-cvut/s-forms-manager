package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.RecordVersion)
public class RecordVersion extends LocalEntity implements Serializable {

    @OWLObjectProperty(iri = Vocabulary.p_hasRecord)
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

