package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;


import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.Transient;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.FormTemplate)
public class FormTemplate extends LocalEntity implements Serializable, HasConnection {

//    @ParticipationConstraints()
//    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplateVersions, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<FormTemplateVersion> formTemplateVersions;

    @Transient
    private String connectionName;

    // key is the ROOT QUESTION hash

    public FormTemplate() {
    }

    public FormTemplate(String rootQuestionHashKey, String connectionName) {
        super(rootQuestionHashKey);

        this.connectionName = connectionName;
//        this.formTemplateVersions = formTemplateVersions;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

//    public List<FormTemplateVersion> getFormTemplateVersions() {
//        return formTemplateVersions;
//    }
//
//    public void setFormTemplateVersions(List<FormTemplateVersion> formTemplateVersions) {
//        this.formTemplateVersions = formTemplateVersions;
//    }
}
