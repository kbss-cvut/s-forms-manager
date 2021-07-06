package cz.cvut.kbss.sformsmanager.model.dto;

public class SubmittedAnswerChangeDTO {

    public final String questionOrigin;
    public final String questionLabel;
    public final String leftAnswerValue;
    public final String rightAnswerValue;

    public SubmittedAnswerChangeDTO(String questionOrigin, String questionLabel, String leftAnswerValue, String rightAnswerValue) {
        this.questionOrigin = questionOrigin;
        this.questionLabel = questionLabel;
        this.leftAnswerValue = leftAnswerValue;
        this.rightAnswerValue = rightAnswerValue;
    }

    public String getQuestionLabel() {
        return questionLabel;
    }

    public String getLeftAnswerValue() {
        return leftAnswerValue;
    }

    public String getRightAnswerValue() {
        return rightAnswerValue;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }
}
