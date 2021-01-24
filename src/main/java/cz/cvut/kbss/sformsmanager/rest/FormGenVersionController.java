package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.FormGenVersionDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenVersionHistogramDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenVersionHistogramDBResponse;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formGenVersion")
public class FormGenVersionController {

    private final FormGenMetadataService metadataService;
    private final FormGenVersionService versionService;

    @Autowired
    public FormGenVersionController(FormGenMetadataService metadataService, FormGenVersionService versionService) {
        this.metadataService = metadataService;
        this.versionService = versionService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FormGenVersionDTO> getFormGenVersions(@RequestParam(value = "connectionName") String connectionName) {

        return versionService.findAllInConnection(connectionName).stream()
                .map(version -> new FormGenVersionDTO(
                        version.getVersion(),
                        version.getUri().toString(),
                        version.getSampleContextUri(),
                        metadataService.getConnectionCountByVersion(connectionName, version.getUri().toString())))
                .collect(Collectors.toList());
    }

    /**
     * Returns map of versions with month occurrence of form completions.
     * <p/>
     * Length of the array depends on the first and the latest completed (processed) forms.
     *
     * @param connectionName
     * @return month occurrence map of the form completions
     * @throws IOException if the query couldn't be completed
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/histogram")
    public FormGenVersionHistogramDTO getVersionsHistogramData(@RequestParam(value = "connectionName") String connectionName) throws IOException {

        FormGenLatestAndNewestDateDBResponse bounds = metadataService.getHistogramBounds(connectionName);
        List<FormGenVersionHistogramDBResponse> histogram = metadataService.getHistogramData(connectionName);

        int datesRange = (bounds.getLatestYear() - bounds.getEarliestYear()) * 12 + (bounds.getLatestMonth() - bounds.getEarliestMonth()) + 1;
        Map<String, Integer[]> histogramsByVersion = new HashMap<>();

        for (FormGenVersionHistogramDBResponse e : histogram) {
            if (!histogramsByVersion.containsKey(e.getVersion())) {
                histogramsByVersion.put(e.getVersion(), new Integer[datesRange]);
            }
            Integer[] versionOccurrenceArray = histogramsByVersion.get(e.getVersion());
            int occurencePosition = (e.getYear() - bounds.getEarliestYear()) * 12 + (e.getMonth() - bounds.getEarliestMonth());

            versionOccurrenceArray[occurencePosition] = e.getCount();
        }

        return new FormGenVersionHistogramDTO(histogramsByVersion,
                bounds.getEarliestYear(),
                bounds.getEarliestMonth(),
                bounds.getLatestYear(),
                bounds.getLatestMonth());
    }
}
