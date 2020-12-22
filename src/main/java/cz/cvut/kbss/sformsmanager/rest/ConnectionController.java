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

import cz.cvut.kbss.sformsmanager.model.dto.ConnectionDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.Connection;
import cz.cvut.kbss.sformsmanager.service.data.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/names")
    public List<String> findAllNames() {

        List<Connection> formGenMetadata = connectionService.findAll();
        return formGenMetadata.stream().map(connection -> connection.getKey()).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<ConnectionDTO> findAll() {

        List<Connection> formGenMetadata = connectionService.findAll();
        return formGenMetadata.stream().map(ConnectionDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void create(
            @RequestBody ConnectionDTO connectionDTO) {

        Connection connection = new Connection(
                connectionDTO.getFormGenRepositoryUrl(),
                connectionDTO.getFormGenServiceUrl(),
                connectionDTO.getAppRepositoryUrl(),
                connectionDTO.getConnectionName()
        );
        connectionService.create(connection);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ConnectionDTO find(
            @RequestParam(value = "connectionName") String connectionName) {

        Optional<Connection> connection = connectionService.findByKey(connectionName);
        return connection.map(ConnectionDTO::new)
                .orElseThrow(() -> new NotFoundException("FormGenInfo " + connectionName + " not found."));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(
            @RequestParam(value = " Connection") ConnectionDTO connectionDTO) {

        Connection connection = new Connection(
                connectionDTO.getFormGenRepositoryUrl(),
                connectionDTO.getFormGenServiceUrl(),
                connectionDTO.getAppRepositoryUrl(),
                connectionDTO.getConnectionName()
        );
        connectionService.update(connection); // TODO: might not work
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(
            @RequestBody String connectionName) {

        Optional<Connection> connection = connectionService.findByKey(connectionName);
        if (connection.isPresent()) {
            connectionService.delete(connection.get());
        }
    }

}
