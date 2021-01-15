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

import cz.cvut.kbss.sformsmanager.model.dto.FormGenMetadataDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.service.data.FormGenJsonLoader;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/formGen")
public class FormGenController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenController.class);

    private final FormGenJsonLoader formGenJsonLoader;
    private final FormGenMetadataService metadataService;

    @Autowired
    public FormGenController(FormGenJsonLoader FormGenJsonLoader, FormGenMetadataService metadataService) {
        this.formGenJsonLoader = FormGenJsonLoader;
        this.metadataService = metadataService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info/get")
    public FormGenMetadataDTO getFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri) {

        String key = OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
        Optional<FormGenMetadata> formGenMetadata = metadataService.findByKey(key);
        if (formGenMetadata.isPresent()) {
            return new FormGenMetadataDTO(formGenMetadata.get());
        } else {
            throw new NotFoundException("FormGenInfo " + key + " not found.");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "s-forms-json-ld")
    public String getFormGenRawJson(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri
    ) throws URISyntaxException {

        return formGenJsonLoader.getFormGenRawJsonFromConnection(connectionName, contextUri).getRawJson();
    }

}
