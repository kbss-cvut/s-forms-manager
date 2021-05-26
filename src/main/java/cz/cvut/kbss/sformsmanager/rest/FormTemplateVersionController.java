package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.dto.FormTemplateVersionDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormTemplateVersionHistogramDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistogramQueryResult;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistoryBoundsQueryResult;
import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.QuestionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formTemplate/version")
public class FormTemplateVersionController {

    private final FormTemplateService formTemplateService;
    private final QuestionTemplateService questionTemplateService;

    @Autowired
    public FormTemplateVersionController(FormTemplateService formTemplateService, QuestionTemplateService questionTemplateService) {
        this.formTemplateService = formTemplateService;
        this.questionTemplateService = questionTemplateService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<FormTemplateVersionDTO> getFormTemplateVersions(@RequestParam(value = "projectName") String projectName) {

        return formTemplateService.findAllVersions(projectName).stream()
                .map(version -> new FormTemplateVersionDTO(
                        version.getKey(),
                        version.getInternalName(),
                        version.getUri().toString(),
                        version.getSampleRemoteContextURI(),
                        questionTemplateService.countSnapshotsWithFormTemplateVersion(projectName, version.getUri()),
                        formTemplateService.countFormTemplateVersionRecordSnapshots(projectName, version.getUri())))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, path = "/setInternalName")
    @ResponseStatus(value = HttpStatus.OK)
    public void setVersionSynonym(@RequestParam(value = "projectName") String projectName,
                                  @RequestParam(value = "versionKey") String formTemplateVersionKey,
                                  @RequestParam(value = "internalName") String internalName) {

        Optional<FormTemplateVersion> versionOpt = formTemplateService.findVersionByKey(projectName, formTemplateVersionKey);
        if (versionOpt.isPresent()) {
            FormTemplateVersion version = versionOpt.get();
            version.setInternalName(internalName.isEmpty() ? null : internalName);
            formTemplateService.updateVersion(projectName, version);
        } else {
            throw new VersionNotFoundException("Version with key " + formTemplateVersionKey + " was not found");
        }
    }


    /**
     * Returns map of versions with month occurrence of form completions.
     * <p/>
     * Length of the array depends on the first and the latest completed (processed) forms.
     *
     * @param projectName
     * @return month occurrence map of the form completions
     * @throws IOException if the query couldn't be completed
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/histogram")
    public FormTemplateVersionHistogramDTO getVersionsHistogramData(@RequestParam(value = "projectName") String projectName) throws IOException {

        VersionHistoryBoundsQueryResult bounds = formTemplateService.getHistogramBounds(projectName);
        List<VersionHistogramQueryResult> histogram = formTemplateService.getHistogramData(projectName);

        int datesRange = (bounds.getLatestYear() - bounds.getEarliestYear()) * 12 + (bounds.getLatestMonth() - bounds.getEarliestMonth()) + 1;
        Map<String, int[]> histogramsByVersion = new TreeMap<>();

        for (VersionHistogramQueryResult e : histogram) {
            if (!histogramsByVersion.containsKey(e.getVersionName())) {
                histogramsByVersion.put(e.getVersionName(), new int[datesRange]);
            }
            int[] versionOccurrenceArray = histogramsByVersion.get(e.getVersionName());
            int occurencePosition = (e.getYear() - bounds.getEarliestYear()) * 12 + (e.getMonth() - bounds.getEarliestMonth());

            versionOccurrenceArray[occurencePosition] += e.getCount();
        }

        return new FormTemplateVersionHistogramDTO(histogramsByVersion,
                bounds.getEarliestYear(),
                bounds.getEarliestMonth(),
                bounds.getLatestYear(),
                bounds.getLatestMonth());
    }
}
