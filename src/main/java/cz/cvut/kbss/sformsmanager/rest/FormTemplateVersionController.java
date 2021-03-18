package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.dto.FormTemplateVersionDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
                        version.getSampleRemoteContextURI().toString()
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
}
