package org.legendre.eventmanagement.host.controller;

import org.legendre.eventmanagement.host.Host;
import org.legendre.eventmanagement.host.HostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequestMapping(HOST_URL)
public class HostRestController {
    private final HostService hostService;

    public HostRestController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping(CREATE_PATH)
    private Host createHost(@RequestBody Host request) {
        return hostService.createHost(request);
    }

    @GetMapping(GET_PATH)
    private Optional<Host> getHost(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return hostService.getHostByName(name);
    }

    @GetMapping
    private List<Host> getHost() {
        return hostService.getAll();
    }

    @PutMapping(UPDATE_PATH)
    private Host updateHost(@RequestBody Host request, @RequestParam String name) {
        return hostService.updateHost(request, name);
    }

    @DeleteMapping(DELETE_PATH)
    private Optional<Host> deleteHost(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return hostService.deleteHost(name);

    }

}
