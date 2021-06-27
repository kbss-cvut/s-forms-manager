package cz.cvut.kbss.sformsmanager.service.ticketing.trello;

import cz.cvut.kbss.sformsmanager.service.ticketing.TicketProjectRelations;

import java.util.Map;

public class TrelloCustomFields implements TicketProjectRelations {

    public static String FORM_VERSION_KEY_CUSTOM_FIELD_ID = "SpecificFormVersionKEY";
    public static String FORM_CONTEXT_URI_CUSTOM_FIELD = "SpecificFormCU";
    public static String QUESTION_ORIGIN_PATH_CUSTOM_FIELD = "SpecificQuestionQOP";

    private final Map<String, String> customFields;

    public TrelloCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
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
}
