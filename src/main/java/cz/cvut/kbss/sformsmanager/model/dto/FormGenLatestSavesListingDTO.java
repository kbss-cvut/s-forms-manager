package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class FormGenLatestSavesListingDTO {

    private final List<FormGenSaveGroupInfoDTO> latestSaves;

    private final int totalNumberOfFormGens;

    public FormGenLatestSavesListingDTO(List<FormGenSaveGroupInfoDTO> latestSaves, int totalNumberOfFormGens) {
        this.latestSaves = latestSaves;
        this.totalNumberOfFormGens = totalNumberOfFormGens;
    }

    public List<FormGenSaveGroupInfoDTO> getLatestSaves() {
        return latestSaves;
    }

    public int getTotalNumberOfFormGens() {
        return totalNumberOfFormGens;
    }
}
