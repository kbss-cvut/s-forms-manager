package cz.cvut.kbss.sformsmanager.rest;
//
//import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
//import cz.cvut.kbss.sformsmanager.model.dto.FormGenVersionDTO;
//import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
//import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
//import cz.cvut.kbss.sformsmanager.service.process.FormGenVersionCompareService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/formGenVersion")
//public class FormGenVersionController {
//
//    private final FormGenMetadataService metadataService;
//    private final FormGenVersionService versionService;
//    private final FormGenVersionCompareService versionCompareService;
//
//    @Autowired
//    public FormGenVersionController(FormGenMetadataService metadataService, FormGenVersionService versionService, FormGenVersionCompareService versionCompareService) {
//        this.metadataService = metadataService;
//        this.versionService = versionService;
//        this.versionCompareService = versionCompareService;
//    }
//
//    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void setVersionSynonym(@RequestParam(value = "versionKey") String versionKey,
//                                  @RequestParam(value = "synonym") String synonym) {
//
//        Optional<FormGenVersion> versionOpt = versionService.findByVersionName(versionKey);
//        if (versionOpt.isPresent()) {
//            FormGenVersion version = versionOpt.get();
//            version.setSynonym(synonym.isEmpty() ? null : synonym);
//            versionService.update(version);
//
//        } else {
//            throw new VersionNotFoundException("Version with key " + versionKey + " was not found");
//        }
//    }
//
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<FormGenVersionDTO> getFormGenVersions(@RequestParam(value = "connectionName") String connectionName) {
//
//        return versionService.findAllInConnection(connectionName).stream()
//                .map(version -> new FormGenVersionDTO(
//                        version.getVersionName(),
//                        version.getUri().toString(),
//                        version.getSampleContextUri(),
//                        metadataService.getConnectionCountByVersion(connectionName, version.getUri().toString()),
//                        version.getSynonym()))
//                .collect(Collectors.toList());
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/compare")
//    public String getFormGenRawJson(
//            @RequestParam(value = "connectionName") String connectionName,
//            @RequestParam(value = "version1") String version1,
//            @RequestParam(value = "version2") String version2
//    ) throws IOException, URISyntaxException {
//        versionCompareService.getMergedVersionsJson(connectionName, version1, version2);
//        return new String(Files.readAllBytes(ResourceUtils.getFile("classpath:form_with_categories.json").toPath()));
//    }
//
//    /**
//     * Returns map of versions with month occurrence of form completions.
//     * <p/>
//     * Length of the array depends on the first and the latest completed (processed) forms.
//     *
//     * @param connectionName
//     * @return month occurrence map of the form completions
//     * @throws IOException if the query couldn't be completed
//     */
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/histogram")
//    public FormGenVersionHistogramDTO getVersionsHistogramData(@RequestParam(value = "connectionName") String connectionName) throws IOException {
//
//        FormGenLatestAndNewestDateDBResponse bounds = metadataService.getHistogramBounds(connectionName);
//        List<FormTemplateVersionHistogramQueryResult> histogram = metadataService.getHistogramData(connectionName);
//
//        int datesRange = (bounds.getLatestYear() - bounds.getEarliestYear()) * 12 + (bounds.getLatestMonth() - bounds.getEarliestMonth()) + 1;
//        Map<String, int[]> histogramsByVersion = new TreeMap<>();
//
//        for (FormTemplateVersionHistogramQueryResult e : histogram) {
//            if (!histogramsByVersion.containsKey(e.getVersionName())) {
//                histogramsByVersion.put(e.getVersionName(), new int[datesRange]);
//            }
//            int[] versionOccurrenceArray = histogramsByVersion.get(e.getVersionName());
//            int occurencePosition = (e.getYear() - bounds.getEarliestYear()) * 12 + (e.getMonth() - bounds.getEarliestMonth());
//
//            versionOccurrenceArray[occurencePosition] += e.getCount();
//        }
//
//        return new FormGenVersionHistogramDTO(histogramsByVersion,
//                bounds.getEarliestYear(),
//                bounds.getEarliestMonth(),
//                bounds.getLatestYear(),
//                bounds.getLatestMonth());
//    }
//}
