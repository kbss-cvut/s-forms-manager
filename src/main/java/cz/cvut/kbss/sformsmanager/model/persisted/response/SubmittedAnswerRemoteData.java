package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.*;

import java.io.Serializable;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@OWLClass(iri = QuestionSnapshotRemoteData.s_c_answer)
public class SubmittedAnswerRemoteData implements Serializable {

    @Id(generated = true)
    protected URI uri;

    @OWLDataProperty(iri = QuestionSnapshotRemoteData.s_p_has_data_value)
    private String textValue;

    @OWLObjectProperty(iri = QuestionSnapshotRemoteData.s_p_has_object_value)
    private URI codeValue;

    @OWLObjectProperty(iri = QuestionSnapshotRemoteData.s_p_has_answer_origin)
    private URI origin;

    @Types
    private Set<String> types = new HashSet<>();

    public SubmittedAnswerRemoteData() {
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public URI getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(URI codeValue) {
        this.codeValue = codeValue;
    }

    public URI getOrigin() {
        return origin;
    }

    public void setOrigin(URI origin) {
        this.origin = origin;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        String out = "Answer{";
        if (textValue != null && codeValue != null) {
            out += "value=" + textValue + ", code=" + codeValue;
        } else if (textValue == null) {
            out += "code=" + codeValue;
        } else {
            out += "text=" + textValue;
        }
        out += '}';
        return out;
    }
}
