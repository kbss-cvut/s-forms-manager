package cz.cvut.kbss.sformsmanager.model.dto;

import com.google.common.base.Objects;

import java.util.List;

public class DagSelectSearchOptionDTO {

    private final String questionOrigin;

    private final String questionOriginPath;

    private final String label;

    private final List<String> subTerm;

    public DagSelectSearchOptionDTO(String label, String questionOrigin, String questionOriginPath, List<String> subTerm) {
        this.label = label;
        this.questionOrigin = questionOrigin;
        this.questionOriginPath = questionOriginPath;
        this.subTerm = subTerm;
    }

    public String getLabel() {
        return label;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DagSelectSearchOptionDTO)) return false;
        DagSelectSearchOptionDTO that = (DagSelectSearchOptionDTO) o;
        return Objects.equal(getQuestionOrigin(), that.getQuestionOrigin()) &&
                Objects.equal(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getQuestionOrigin(), getLabel());
    }

    public List<String> getSubTerm() {
        return subTerm;
    }

    public String getQuestionOriginPath() {
        return questionOriginPath;
    }
}
