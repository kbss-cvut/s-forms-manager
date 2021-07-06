package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.SubmittedAnswer)
public class SubmittedAnswer extends LocalEntity implements Serializable {

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.p_hasAnswerValue)
    private String textValue;

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.s_p_has_question_origin)
    private String questionOrigin;

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.s_p_label)
    private String questionLabel;

    // key is the QUESTION-ORIGIN + ANSWER VALUE -> hash

    public SubmittedAnswer() {
    }

    public SubmittedAnswer(String questionOriginAndAnswerValueHashKey, String textValue, String questionOrigin, String questionLabel) {
        super(questionOriginAndAnswerValueHashKey);
        this.textValue = textValue;
        this.questionOrigin = questionOrigin;
        this.questionLabel = questionLabel;
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

    public String getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(String questionLabel) {
        this.questionLabel = questionLabel;
    }
}
