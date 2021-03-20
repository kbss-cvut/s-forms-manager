package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenVersionHistogramDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormTemplateVersionDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormTemplateVersionHistogramQueryResult;
import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
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

    @Autowired
    public FormTemplateVersionController(FormTemplateService formTemplateService) {
        this.formTemplateService = formTemplateService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<FormTemplateVersionDTO> getFormTemplateVersions(@RequestParam(value = "projectName") String projectName) {

        return formTemplateService.findAllVersions(projectName).stream()
                .map(version -> new FormTemplateVersionDTO(
                        version.getKey(),
                        version.getInternalName(),
                        version.getUri().toString(),
                        version.getSampleRemoteContextURI()
                ))
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
    public FormGenVersionHistogramDTO getVersionsHistogramData(@RequestParam(value = "projectName") String projectName) throws IOException {

        FormGenLatestAndNewestDateDBResponse bounds = formTemplateService.getHistogramBounds(projectName);
        List<FormTemplateVersionHistogramQueryResult> histogram = formTemplateService.getHistogramData(projectName);

        int datesRange = (bounds.getLatestYear() - bounds.getEarliestYear()) * 12 + (bounds.getLatestMonth() - bounds.getEarliestMonth()) + 1;
        Map<String, int[]> histogramsByVersion = new TreeMap<>();

        for (FormTemplateVersionHistogramQueryResult e : histogram) {
            if (!histogramsByVersion.containsKey(e.getVersionName())) {
                histogramsByVersion.put(e.getVersionName(), new int[datesRange]);
            }
            int[] versionOccurrenceArray = histogramsByVersion.get(e.getVersionName());
            int occurencePosition = (e.getYear() - bounds.getEarliestYear()) * 12 + (e.getMonth() - bounds.getEarliestMonth());

            versionOccurrenceArray[occurencePosition] += e.getCount();
        }

        return new FormGenVersionHistogramDTO(histogramsByVersion,
                bounds.getEarliestYear(),
                bounds.getEarliestMonth(),
                bounds.getLatestYear(),
                bounds.getLatestMonth());
    }
}
