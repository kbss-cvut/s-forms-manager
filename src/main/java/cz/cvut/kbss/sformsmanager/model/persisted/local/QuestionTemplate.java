package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.util.Set;

@OWLClass(iri = Vocabulary.QuestionTemplate)
public class QuestionTemplate extends LocalEntity implements Serializable {

    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate)
    // TODO: can be LAZILY loaded objects @ParticipationConstraints(nonEmpty = true)?
    private FormTemplate formTemplate;

    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshots)
    private Set<QuestionTemplateSnapshot> questionTemplateSnapshots;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_originPath)
    private String questionOrigin;

    // key is the QUESTION-ORIGIN -> hash

    public QuestionTemplate() {
    }

    public QuestionTemplate(String questionOriginHashKey, String questionOrigin, Set<QuestionTemplateSnapshot> questionTemplateSnapshots) {
        super(questionOriginHashKey);
        this.questionOrigin = questionOrigin;
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    public Set<QuestionTemplateSnapshot> getSnapshots() {
        return questionTemplateSnapshots;
    }

    public void setSnapshots(Set<QuestionTemplateSnapshot> questionTemplateSnapshots) {
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }
}
