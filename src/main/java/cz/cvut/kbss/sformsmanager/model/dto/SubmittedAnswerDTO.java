package cz.cvut.kbss.sformsmanager.model.dto;

public class SubmittedAnswerDTO {

    public final String questionOrigin;
    public final String questionLabel;
    public final String answerValue;

    public SubmittedAnswerDTO(String questionOrigin, String questionLabel, String answerValue) {
        this.questionOrigin = questionOrigin;
        this.questionLabel = questionLabel;
        this.answerValue = answerValue;
    }


    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public String getQuestionLabel() {
        return questionLabel;
    }
}
