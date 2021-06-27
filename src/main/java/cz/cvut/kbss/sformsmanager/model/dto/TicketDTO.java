package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.service.ticketing.TicketProjectRelations;

public class TicketDTO {

    private final String name;

    private final String description;

    private final String url;

    private final TicketRelationsDTO relations;

    public TicketDTO(String name, String description, String url, TicketRelationsDTO relations) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.relations = relations;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TicketRelationsDTO getRelations() {
        return relations;
    }

    public String getUrl() {
        return url;
    }

    public static class TicketRelationsDTO {
        private final String relatedForm;
        private final String relatedFormVersion;
        private final String relatedQuestionOriginPath;
        private final String relatedQuestionLabel;

        public static TicketRelationsDTO createFormTicketRelations(TicketProjectRelations ticketRelations, String relatedQuestionLabel) {
            return new TicketRelationsDTO(ticketRelations.getRelatedRecordSnapshot(),
                    ticketRelations.getRelatedFormVersion(),
                    ticketRelations.getRelatedQuestionOriginPath(),
                    relatedQuestionLabel);
        }

        public TicketRelationsDTO(String relatedForm, String relatedFormVersion, String relatedQuestionOriginPath, String relatedQuestionLabel) {
            this.relatedForm = relatedForm;
            this.relatedFormVersion = relatedFormVersion;
            this.relatedQuestionOriginPath = relatedQuestionOriginPath;
            this.relatedQuestionLabel = relatedQuestionLabel;
        }

        public String getRelatedForm() {
            return relatedForm;
        }

        public String getRelatedFormVersion() {
            return relatedFormVersion;
        }

        public String getRelatedQuestionOriginPath() {
            return relatedQuestionOriginPath;
        }

        public String getRelatedQuestionLabel() {
            return relatedQuestionLabel;
        }

    }
}
