package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class SubmittedAnswersCompareResultDTO {

    private final int numberOfUnchangedAnswers;
    private final List<SubmittedAnswerDTO> leftAnswers;
    private final List<SubmittedAnswerDTO> rightAnswers;
    private final List<SubmittedAnswerChangeDTO> changedAnswers;

    public SubmittedAnswersCompareResultDTO(int numberOfUnchangedAnswers, List<SubmittedAnswerDTO> leftAnswers, List<SubmittedAnswerDTO> rightAnswers, List<SubmittedAnswerChangeDTO> changedAnswers) {
        this.numberOfUnchangedAnswers = numberOfUnchangedAnswers;
        this.leftAnswers = leftAnswers;
        this.rightAnswers = rightAnswers;
        this.changedAnswers = changedAnswers;
    }

    public List<SubmittedAnswerDTO> getRightAnswers() {
        return rightAnswers;
    }

    public List<SubmittedAnswerChangeDTO> getChangedAnswers() {
        return changedAnswers;
    }

    public List<SubmittedAnswerDTO> getLeftAnswers() {
        return leftAnswers;
    }

    public int getNumberOfUnchangedAnswers() {
        return numberOfUnchangedAnswers;
    }
}
