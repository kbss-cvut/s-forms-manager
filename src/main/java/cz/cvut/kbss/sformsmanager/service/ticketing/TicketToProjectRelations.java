package cz.cvut.kbss.sformsmanager.service.ticketing;

public interface TicketToProjectRelations {

    public String getRelatedRecordSnapshot();

    public String getRelatedFormVersion();

    public String getRelatedQuestionOriginPath();
}
