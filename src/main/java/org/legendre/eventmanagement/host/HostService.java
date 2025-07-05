package org.legendre.eventmanagement.host;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HostService {
    private final List<Host> hosts = new ArrayList<>();

    public Host createHost(Host request) {
        hosts.add(request);
        return new Host(request.getName(), request.getEmail(), request.getBio());
    }

    @PostConstruct
    public List<Host> populateHosts() {
        hosts.add(new Host("Padding Technologies", "padding@gmail.com", "Your number one stop for brand visibility and growth"));
        return hosts;
    }

    public Optional<Host> getHostByName(String name) {
        var foundHost = hosts.stream().filter(host -> host.getName().equalsIgnoreCase(name))
                .findFirst();
        foundHost.ifPresentOrElse((
                        host -> System.out.println("Found host: " + name)),
                () -> System.err.println("No host found with name: " + name));
        return foundHost;
    }

    public List<Host> getAll() {
        return hosts;
    }

    public Host updateHost(Host request, String name) {
        var findHost = getHostByName(name);

        Host hostToUpdate = findHost.get();
        hostToUpdate.setName(request.getName());
        hostToUpdate.setEmail(request.getEmail());
        hostToUpdate.setBio(request.getBio());
        return hostToUpdate;
    }

    public Optional<Host> deleteHost(String name) {
        Optional<Host> hostToDelete = getHostByName(name);

        hostToDelete.ifPresentOrElse(host -> {
            hosts.remove(hostToDelete.get());
            System.out.println("Deleted host: " + name);
        }, () -> System.err.println("No host found with name: " + name));
        return hostToDelete;
    }
}
