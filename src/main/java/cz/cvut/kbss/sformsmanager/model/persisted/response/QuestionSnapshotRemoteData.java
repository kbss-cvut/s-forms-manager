package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.io.Serializable;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@OWLClass(iri = QuestionSnapshotRemoteData.s_c_question)
public class QuestionSnapshotRemoteData implements Serializable {

    @Id(generated = true)
    protected URI uri;

    @OWLObjectProperty(iri = s_p_has_related_question, fetch = FetchType.EAGER)
    private Set<QuestionSnapshotRemoteData> subQuestions = new HashSet<>();

    @OWLObjectProperty(iri = s_p_has_answer, fetch = FetchType.EAGER)
    private Set<SubmittedAnswerRemoteData> answers = new HashSet<>();

    @OWLObjectProperty(iri = s_p_has_question_origin)
    private String questionOrigin;

//    @Types
//    private Set<String> types = new HashSet<>();

//    return em.createNativeQuery("SELECT ?x ?answer WHERE { ?x a ?typeQuestion . ?x ?typeHasAnswer ?answer . ?answer a ?typeAnswer  } LIMIT 10")
//            .setParameter("typeQuestion", URI.create("http://onto.fel.cvut.cz/ontologies/documentation/question"))
//            .setParameter("typeAnswer", URI.create("http://onto.fel.cvut.cz/ontologies/documentation/answer"))
//            .setParameter("typeHasAnswer", URI.create("http://onto.fel.cvut.cz/ontologies/documentation/has_answer"))
//            .getResultList();

// doc:question ne
// http://onto.fel.cvut.cz/ontologies/documentation/answer ne

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

//    public Set<String> getTypes() {
//        return types;
//    }
//
//    public void setTypes(Set<String> types) {
//        this.types = types;
//    }

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


    public final static String s_c_Thing = "http://www.w3.org/2002/07/owl#Thing";
    public final static String s_c_answer = "http://onto.fel.cvut.cz/ontologies/documentation/answer";
    public final static String s_c_question = "http://onto.fel.cvut.cz/ontologies/documentation/question";
    public final static String s_c_question_template = "http://onto.fel.cvut.cz/ontologies/form/question-template";
    public final static String s_c_action_history = "http://vfn.cz/ontologies/study-manager/action-history";
    public final static String s_c_administrator = "http://vfn.cz/ontologies/study-manager/administrator";
    public final static String s_c_doctor = "http://vfn.cz/ontologies/study-manager/doctor";
    public final static String s_c_institution = "http://vfn.cz/ontologies/study-manager/institution";
    public final static String s_c_patient_record = "http://vfn.cz/ontologies/study-manager/patient-record";
    public final static String s_c_user = "http://vfn.cz/ontologies/study-manager/user";
    public final static String s_p_has_answer = "http://onto.fel.cvut.cz/ontologies/documentation/has_answer";
    public final static String s_p_has_object_value = "http://onto.fel.cvut.cz/ontologies/documentation/has_object_value";
    public final static String s_p_has_related_question = "http://onto.fel.cvut.cz/ontologies/documentation/has_related_question";
    public final static String s_p_has_answer_origin = "http://onto.fel.cvut.cz/ontologies/form/has-answer-origin";

    public final static String s_p_has_preceding_template = "http://onto.fel.cvut.cz/ontologies/form/has-preceding-template";
    public final static String s_p_has_question_origin = "http://onto.fel.cvut.cz/ontologies/form/has-question-origin";
    public final static String s_p_modified = "http://purl.org/dc/terms/modified";
    public final static String s_p_has_author = "http://vfn.cz/ontologies/study-manager/has-author";
    public final static String s_p_has_last_editor = "http://vfn.cz/ontologies/study-manager/has-last-editor";
    public final static String s_p_has_member = "http://vfn.cz/ontologies/study-manager/has-member";
    public final static String s_p_has_owner = "http://vfn.cz/ontologies/study-manager/has-owner";
    public final static String s_p_has_part = "http://vfn.cz/ontologies/study-manager/has-part";
    public final static String s_p_has_question = "http://vfn.cz/ontologies/study-manager/has-question";
    public final static String s_p_is_member_of = "http://vfn.cz/ontologies/study-manager/is-member-of";
    public final static String s_p_relates_to = "http://vfn.cz/ontologies/study-manager/relates-to";
    public final static String s_p_was_treated_at = "http://vfn.cz/ontologies/study-manager/was-treated-at";
    public final static String s_p_has_data_value = "http://onto.fel.cvut.cz/ontologies/documentation/has_data_value";
    public final static String s_p_has_origin_path_id = "http://onto.fel.cvut.cz/ontologies/form/has-origin-path-id";
    public final static String s_p_created = "http://purl.org/dc/terms/created";
    public final static String s_p_action_type = "http://vfn.cz/ontologies/study-manager/action_type";
    public final static String s_p_formType = "http://vfn.cz/ontologies/study-manager/formType";
    public final static String s_p_isInvited = "http://vfn.cz/ontologies/study-manager/isInvited";
    public final static String s_p_key = "http://vfn.cz/ontologies/study-manager/key";
    public final static String s_p_password = "http://vfn.cz/ontologies/study-manager/password";
    public final static String s_p_payload = "http://vfn.cz/ontologies/study-manager/payload";
    public final static String s_p_token = "http://vfn.cz/ontologies/study-manager/token";
    public final static String s_p_accountName = "http://xmlns.com/foaf/0.1/accountName";
    public final static String s_p_firstName = "http://xmlns.com/foaf/0.1/firstName";
    public final static String s_p_lastName = "http://xmlns.com/foaf/0.1/lastName";
    public final static String s_p_mbox = "http://xmlns.com/foaf/0.1/mbox";
    public final static String s_p_comment = "http://www.w3.org/2000/01/rdf-schema#comment";
    public final static String s_p_label = "http://www.w3.org/2000/01/rdf-schema#label";

}
