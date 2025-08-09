package org.legendre.eventmanagement.host.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.host.model.Host;
import org.legendre.eventmanagement.host.model.HostRequest;
import org.legendre.eventmanagement.host.service.HostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(HOST_URL)
public class HostRestController {

    private final HostService hostService;

    @PostMapping(CREATE_PATH)
    private ResponseEntity<Host> createHost(@RequestBody @Valid HostRequest request) {
        return new ResponseEntity<>(hostService.createHost(request), HttpStatus.OK);
    }

    @GetMapping(GET_PATH)
    private ResponseEntity<Optional<Host>> getHost(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return new ResponseEntity<>(hostService.getHostByName(name), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Host>> getHost() {
        return new ResponseEntity<>(hostService.getAll(), HttpStatus.OK);
    }

    @PutMapping(UPDATE_PATH)
    private ResponseEntity<Host> updateHost(@RequestBody @Valid HostRequest request, @RequestParam String email) {
        return new ResponseEntity<>(hostService.updateHost(request, email), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PATH)
    private ResponseEntity<Void> deleteHost(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        hostService.deleteHost(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
