package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.jopa.model.annotations.Transient;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.SubmittedAnswer)
public class SubmittedAnswer extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.p_hasAnswerValue)
    private String textValue;

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.p_originPath)
    private String questionOrigin;

    // key is the QUESTION-ORIGIN + ANSWER VALUE -> hash

    @Transient
    private String connectionName;

    public SubmittedAnswer() {
    }

    public SubmittedAnswer(String questionOriginAndAnswerValueHashKey, String textValue, String questionOrigin, String connectionName) {
        super(questionOriginAndAnswerValueHashKey);
        this.textValue = textValue;
        this.questionOrigin = questionOrigin;
        this.connectionName = connectionName;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}
