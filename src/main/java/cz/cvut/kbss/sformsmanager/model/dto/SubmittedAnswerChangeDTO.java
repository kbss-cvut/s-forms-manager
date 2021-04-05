package cz.cvut.kbss.sformsmanager.model.dto;

public class SubmittedAnswerChangeDTO {

    public final String question;
    public final String leftAnswerValue;
    public final String rightAnswerValue;

    public SubmittedAnswerChangeDTO(String question, String leftAnswerValue, String rightAnswerValue) {
        this.question = question;
        this.leftAnswerValue = leftAnswerValue;
        this.rightAnswerValue = rightAnswerValue;
    }

    public String getQuestion() {
        return question;
    }

    public String getLeftAnswerValue() {
        return leftAnswerValue;
    }

    public String getRightAnswerValue() {
        return rightAnswerValue;
    }
}
