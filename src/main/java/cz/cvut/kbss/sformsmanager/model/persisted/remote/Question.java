package cz.cvut.kbss.sformsmanager.model.persisted.remote;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.Question)
public class Question implements Serializable {

    @Id(generated = true)
    protected URI uri;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_question_origin)
    private URI questionOrigin;

    public Question() {
    }

    public Question(URI uri, URI questionOrigin) {
        this.uri = uri;
        this.questionOrigin = questionOrigin;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(URI questionOrigin) {
        this.questionOrigin = questionOrigin;
    }
}