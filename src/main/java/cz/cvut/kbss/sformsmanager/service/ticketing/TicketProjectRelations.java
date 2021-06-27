package cz.cvut.kbss.sformsmanager.service.ticketing;

public interface TicketProjectRelations {

    public String getRelatedRecordSnapshot();

    public String getRelatedFormVersion();

    public String getRelatedQuestionOriginPath();
}
