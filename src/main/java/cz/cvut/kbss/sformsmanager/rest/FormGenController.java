/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.sformsmanager.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenMetadataDto;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.service.ConnectedRepositoryService;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequestMapping("/formGen")
@RequiredArgsConstructor
public class FormGenController {

    private final ConnectedRepositoryService connectedRepositoryService;
    private final FormGenMetadataDAO formGenMetadataDAO;

    @RequestMapping(method = RequestMethod.POST, path = "/info/get")
    public FormGenMetadataDto getFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri) {

        String key = OWLUtils.createFormGenMetadataKey(connectionName, contextUri);
        Optional<FormGenMetadata> formGenMetadata = formGenMetadataDAO.findByKey(key);
        if (formGenMetadata.isPresent()) {
            return new FormGenMetadataDto(formGenMetadata.get());
        } else {
            throw new NotFoundException("FormGenInfo " + key + " not found.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getFormGenRawJson(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri
    ) throws URISyntaxException, JsonProcessingException {

        return connectedRepositoryService.getFormGenRawJsonFromConnectionAndSaveMetadata(connectionName, contextUri).getRawJson();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri)
            throws URISyntaxException, JsonProcessingException {

        connectedRepositoryService.getFormGenRawJsonFromConnectionAndSaveMetadata(connectionName, contextUri).getRawJson();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/static")
    public String getFormStatic(@RequestParam(value = "contextUri") String contextUri) throws IOException {
        return new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
    }
}
