package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class FormGenMetadataListingDTO {

    private final List<FormGenMetadataDTO> formGens;

    private final int totalNumberOfFormGens;


    public FormGenMetadataListingDTO(List<FormGenMetadataDTO> formGens, int totalNumberOfFormGens) {
        this.formGens = formGens;
        this.totalNumberOfFormGens = totalNumberOfFormGens;
    }

    public List<FormGenMetadataDTO> getFormGens() {
        return formGens;
    }

    public int getTotalNumberOfFormGens() {
        return totalNumberOfFormGens;
    }
}
