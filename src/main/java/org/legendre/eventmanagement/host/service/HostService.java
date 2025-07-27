package org.legendre.eventmanagement.host.service;

import org.legendre.eventmanagement.host.model.Host;
import org.legendre.eventmanagement.host.model.HostRequest;

import java.util.List;
import java.util.Optional;

public interface HostService {
    Host createHost(HostRequest request);

    Optional<Host> getHostByName(String name);

    List<Host> getAll();

    Host updateHost(HostRequest request, String email);

    void deleteHost(String name);
}
