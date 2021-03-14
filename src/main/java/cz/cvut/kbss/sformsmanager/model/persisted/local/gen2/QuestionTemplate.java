package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;
import java.util.List;

@OWLClass(iri = Vocabulary.QuestionTemplate)
public class QuestionTemplate extends LocalEntity implements Serializable, HasConnection {

    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate)
    // TODO: can be LAZILY loaded objects @ParticipationConstraints(nonEmpty = true)?
    private FormTemplate formTemplate;

    @Sequence
    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshots)
    private List<QuestionTemplateSnapshot> questionTemplateSnapshots;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_originPath)
    private String questionOrigin;

    // key is the QUESTION-ORIGIN -> hash

    @Transient
    private String connectionName;

    public QuestionTemplate() {
    }

    public QuestionTemplate(String questionOriginHashKey, String questionOrigin, List<QuestionTemplateSnapshot> questionTemplateSnapshots, String connectionName) {
        super(questionOriginHashKey);
        this.questionOrigin = questionOrigin;
        this.questionTemplateSnapshots = questionTemplateSnapshots;
        this.connectionName = connectionName;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    public List<QuestionTemplateSnapshot> getSnapshots() {
        return questionTemplateSnapshots;
    }

    public void setSnapshots(List<QuestionTemplateSnapshot> questionTemplateSnapshots) {
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}
