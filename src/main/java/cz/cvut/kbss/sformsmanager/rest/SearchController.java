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
import cz.cvut.kbss.sformsmanager.model.dto.FormGenMetadataListingDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.service.process.SearchQueryBuilder;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchController.class);

    private final freemarker.template.Configuration templateCfg;

    private final FormGenMetadataService metadataService;

    @Autowired
    public SearchController(FormGenMetadataService metadataService, freemarker.template.Configuration templateCfg) {
        this.metadataService = metadataService;
        this.templateCfg = templateCfg;
    }

    @RequestMapping(path = "/updateQuery", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String updateSearchQuery(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "versions") List<String> versions,
            @RequestParam(value = "saveHashes") List<String> saveHashes,
            @RequestParam(value = "latestSaves") boolean latestSaves) throws IOException, TemplateException {

        SearchQueryBuilder queryBuilder = new SearchQueryBuilder(templateCfg);
        queryBuilder.setConnectionName(connectionName);
        queryBuilder.setSaveHashes(saveHashes);
        queryBuilder.setLatestSaves(latestSaves);
        queryBuilder.setVersions(versions);
        return queryBuilder.build();

    }

    @PostMapping(path = "/runQuery")
    @ResponseStatus(value = HttpStatus.OK)
    public FormGenMetadataListingDTO runSearchQuery(
            @RequestBody SearchQueryRequest request) {

        int totalFormGens = metadataService.getConnectionCount(request.getConnectionName());
        List<FormGenMetadataDTO> formGens = metadataService.runSearchQuery(request.getQuery()).stream()
                .map(FormGenMetadataDTO::new)
                .collect(Collectors.toList());

        return new FormGenMetadataListingDTO(formGens, totalFormGens);

    }

    private static class SearchQueryRequest {
        private String query;

        private String connectionName;

        public SearchQueryRequest() {
        }

        public String getConnectionName() {
            return connectionName;
        }

        public void setConnectionName(String connectionName) {
            this.connectionName = connectionName;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}
