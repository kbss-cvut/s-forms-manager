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

import cz.cvut.kbss.sformsmanager.model.JsonLDForm;
import cz.cvut.kbss.sformsmanager.service.DataRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final DataRepositoryService dataService;


    @RequestMapping(method = RequestMethod.POST, path = "compare")
    public String compareTwoForms(@RequestParam(value = "connectionName") String connectionName,
                                  @RequestParam(value = "contextUri1") String contextUri1,
                                  @RequestParam(value = "contextUri2") String contextUri2) throws URISyntaxException {
        JsonLDForm form1 = dataService.getFormFromConnection(connectionName, contextUri1);
        JsonLDForm form2 = dataService.getFormFromConnection(connectionName, contextUri2);

        return "yes";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getForm(@RequestParam(value = "connectionName") String connectionName,
                          @RequestParam(value = "contextUri") String contextUri) throws URISyntaxException {
        return dataService.getFormFromConnection(connectionName, contextUri).getRawJson();

        // todo: handle exception properly
    }

    @RequestMapping(method = RequestMethod.POST, path = "/static")
    public String getFormStatic(@RequestParam(value = "contextUri") String contextUri) throws IOException {
        return new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
    }
}
