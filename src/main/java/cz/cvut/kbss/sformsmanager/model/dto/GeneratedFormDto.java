package cz.cvut.kbss.sformsmanager.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.model.GeneratedForm;
import lombok.Data;

@Data
public class GeneratedFormDto {

    private int hashCode;

    public GeneratedFormDto(GeneratedForm generatedForm) throws JsonProcessingException {
        this.hashCode = generatedForm.getObjectRepresentation().getGraph().hashCode();
    }

}
