package cz.cvut.kbss.sformsmanager.service.ticketing.trello;

import cz.cvut.kbss.sformsmanager.service.ticketing.TicketToProjectRelations;

import java.util.HashMap;
import java.util.Map;

public class TrelloCustomFields implements TicketToProjectRelations {

    public static String FORM_VERSION_KEY_CUSTOM_FIELD_ID = "SpecificFormVersionKEY";
    public static String FORM_CONTEXT_URI_CUSTOM_FIELD = "SpecificFormCU";
    public static String QUESTION_ORIGIN_PATH_CUSTOM_FIELD = "SpecificQuestionQOP";

    private final Map<String, String> customFields;

    public TrelloCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public TrelloCustomFields(String formRelationId, String formVersionRelationId, String questionRelationId) {
        this.customFields = new HashMap<>();
        if (formRelationId != null && !formRelationId.isEmpty())
            customFields.put(FORM_CONTEXT_URI_CUSTOM_FIELD, formRelationId);
        if (formVersionRelationId != null && !formVersionRelationId.isEmpty())
            customFields.put(FORM_VERSION_KEY_CUSTOM_FIELD_ID, formVersionRelationId);
        if (questionRelationId != null && !questionRelationId.isEmpty())
            customFields.put(QUESTION_ORIGIN_PATH_CUSTOM_FIELD, questionRelationId);
    }

    @Override
    public String getRelatedRecordSnapshot() {
        return customFields.get(FORM_CONTEXT_URI_CUSTOM_FIELD);
    }

    @Override
    public String getRelatedFormVersion() {
        return customFields.get(FORM_VERSION_KEY_CUSTOM_FIELD_ID);
    }

    @Override
    public String getRelatedQuestionOriginPath() {
        return customFields.get(QUESTION_ORIGIN_PATH_CUSTOM_FIELD);
    }

    public Map<String, String> getMap() {
        return customFields;
    }
}
