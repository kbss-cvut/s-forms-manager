package cz.cvut.kbss.sformsmanager.model.persisted.local;


import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.FormTemplate)
public class FormTemplate extends LocalEntity implements Serializable {

    // key is the ROOT QUESTION hash

    public FormTemplate() {
    }

    public FormTemplate(String rootQuestionHashKey) {
        super(rootQuestionHashKey);
    }
}
