package cz.cvut.kbss.sformsmanager.model.dto;

public class SubmittedAnswerDTO {

    public final String question;
    public final String answerValue;

    public SubmittedAnswerDTO(String question, String answerValue) {
        this.question = question;
        this.answerValue = answerValue;
    }


    public String getQuestion() {
        return question;
    }

    public String getAnswerValue() {
        return answerValue;
    }
}
