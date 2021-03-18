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

import cz.cvut.kbss.sformsmanager.model.dto.ContextsStatsDTO;
import cz.cvut.kbss.sformsmanager.model.dto.ProjectStatsDTO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.SubmittedAnswerDAO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.QuestionTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project/stats")
public class ProjectStatsController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectStatsController.class);

    private final ContextService contextService;
    private final RecordService recordService;
    private final FormTemplateService formTemplateService;
    private final QuestionTemplateService questionTemplateService;
    private final SubmittedAnswerDAO submittedAnswerDAO;

    @Autowired
    public ProjectStatsController(ContextService contextService, RecordService recordService, FormTemplateService formTemplateService, QuestionTemplateService questionTemplateService, SubmittedAnswerDAO submittedAnswerDAO) {
        this.contextService = contextService;
        this.recordService = recordService;
        this.formTemplateService = formTemplateService;
        this.questionTemplateService = questionTemplateService;
        this.submittedAnswerDAO = submittedAnswerDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contexts")
    @ResponseStatus(value = HttpStatus.OK)
    public ContextsStatsDTO getContextsStats(
            @RequestParam(value = "projectName") String projectName) {

        int totalContexts = contextService.count(projectName);
        int processedContexts = recordService.countRecordSnapshots(projectName);
        return new ContextsStatsDTO(totalContexts, processedContexts);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/forms")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectStatsDTO getFormGenStats(
            @RequestParam(value = "projectName") String projectName) {

        int totalContexts = contextService.count(projectName);
        int recordVersions = recordService.countRecordVersions(projectName);
        int recordSnapshots = recordService.countRecordSnapshots(projectName);
        int records = recordService.countRecords(projectName);

        int formTemplates = formTemplateService.countFormTemplates(projectName);
        int formTemplateVersions = formTemplateService.countFormTemplates(projectName);

        int questionTemplates = questionTemplateService.countQuestionTemplates(projectName);
        int questionTemplateSnapshots = questionTemplateService.countQuestionTemplateSnapshots(projectName);
        int answers = submittedAnswerDAO.count(projectName);

        return new ProjectStatsDTO(totalContexts, recordVersions, recordSnapshots, records, formTemplates, formTemplateVersions, questionTemplates, questionTemplateSnapshots, answers);
    }
}
