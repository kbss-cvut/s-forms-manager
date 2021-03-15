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

import cz.cvut.kbss.sformsmanager.model.dto.ProjectDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.service.model.local.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/connections")
public class ConnectionController {

    private final ProjectService projectService;

    @Autowired
    public ConnectionController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/names")
    public List<String> findAllNames() {

        List<Project> formGenMetadata = projectService.findAll();
        return formGenMetadata.stream().map(connection -> connection.getKey()).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<ProjectDTO> findAll() {

        List<Project> formGenMetadata = projectService.findAll();
        return formGenMetadata.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody ProjectDTO projectDTO) {

        Project project = new Project(
                projectDTO.getFormGenRepositoryUrl(),
                projectDTO.getFormGenServiceUrl(),
                projectDTO.getAppRepositoryUrl(),
                projectDTO.getConnectionName()
        );
        projectService.create(project);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ProjectDTO find(@RequestParam(value = "connectionName") String connectionName) {

        Optional<Project> connection = projectService.findByKey(connectionName);
        return connection.map(ProjectDTO::new)
                .orElseThrow(() -> new NotFoundException("FormGenInfo " + connectionName + " not found."));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestParam(value = "projectDTO") ProjectDTO projectDTO) {

        Project project = new Project(
                projectDTO.getFormGenRepositoryUrl(),
                projectDTO.getFormGenServiceUrl(),
                projectDTO.getAppRepositoryUrl(),
                projectDTO.getConnectionName()
        );
        projectService.update(project); // TODO: might not work
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestBody String connectionName) {

        Optional<Project> connection = projectService.findByKey(connectionName);
        if (connection.isPresent()) {
            projectService.delete(connection.get());
        }
    }

}
