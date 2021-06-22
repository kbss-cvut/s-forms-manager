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

import cz.cvut.kbss.sformsmanager.model.dto.DagSelectSearchOptionDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.QuestionTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.SearchService;
import cz.cvut.kbss.sformsmanager.service.search.SearchQueryTemplateService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchController.class);

    private final freemarker.template.Configuration templateCfg;
    private final SearchService searchService;
    private final SearchQueryTemplateService searchQueryTemplateService;
    private final QuestionTemplateService questionTemplateService;

    @Autowired
    public SearchController(Configuration templateCfg, SearchService searchService, SearchQueryTemplateService searchQueryTemplateService, QuestionTemplateService questionTemplateService, FormTemplateService formTemplateService) {
        this.templateCfg = templateCfg;
        this.searchService = searchService;
        this.searchQueryTemplateService = searchQueryTemplateService;
        this.questionTemplateService = questionTemplateService;
    }

    @RequestMapping(path = "/updateQuery", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String getSearchQuery(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "queryId") String queryId,
            @RequestParam(value = "parameter1") String parameter1,
            @RequestParam(value = "parameter2") String parameter2
    ) throws IOException, TemplateException {

        return searchQueryTemplateService.getQuery(projectName, queryId, parameter1, parameter2);
    }

    @PostMapping(path = "/runQuery") // TODO: unify this PostMapping vs RequestMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Object> runSearchQuery(
            @RequestBody SearchQueryRequest request) {

        List<Object> searchResults = searchService.runSearchQuery(request.getQuery()).stream().collect(Collectors.toList());

        return searchResults;
    }

    @RequestMapping(path = "/select/options", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @Transactional
    public List<DagSelectSearchOptionDTO> getDagSelectOptions(
            @RequestParam(value = "projectName") String projectName) {

        return questionTemplateService.findAllSnapshots(projectName).stream()
                .map(qts -> new DagSelectSearchOptionDTO(
                        qts.getLabel(),
                        qts.getQuestionOrigin(),
                        qts.getQuestionOriginPath(),
                        qts.getQuestionTemplateSnapshots() != null
                                ? qts.getQuestionTemplateSnapshots().stream().map(q -> q.getQuestionOriginPath()).collect(Collectors.toList()) : Collections.emptyList()
                )).collect(Collectors.toList());
    }

    @RequestMapping(path = "/getAutocomplete", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getAutocomplete(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "depth") int depth,
            @RequestParam(value = "questionOriginPath") String questionOriginPath) throws IOException {  // TODO: handle all the exceptions in controller layer

        Integer depthString = Integer.valueOf(depth); // TODO: fix this
        return searchService.getAutocompleteValues(projectName, depthString, questionOriginPath);
    }

    private static class SearchQueryRequest {
        private String query;

        private String projectName;

        public SearchQueryRequest() {
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}
