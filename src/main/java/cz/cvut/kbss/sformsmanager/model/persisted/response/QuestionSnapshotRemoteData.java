package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@OWLClass(iri = Vocabulary.s_c_question)
public class QuestionSnapshotRemoteData implements Serializable {

    @Id(generated = true)
    protected URI uri;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_related_question, fetch = FetchType.EAGER)
    private Set<QuestionSnapshotRemoteData> subQuestions = new HashSet<>();

    @OWLObjectProperty(iri = Vocabulary.s_p_has_answer, fetch = FetchType.EAGER)
    private Set<SubmittedAnswerRemoteData> answers = new HashSet<>();

    @OWLObjectProperty(iri = Vocabulary.s_p_has_question_origin)
    private String questionOrigin;

    @OWLDataProperty(iri = Vocabulary.s_p_label)
    private String label;

    public QuestionSnapshotRemoteData() {
    }

    public Set<QuestionSnapshotRemoteData> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(Set<QuestionSnapshotRemoteData> subQuestions) {
        this.subQuestions = subQuestions;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Set<SubmittedAnswerRemoteData> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SubmittedAnswerRemoteData> answers) {
        this.answers = answers;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
