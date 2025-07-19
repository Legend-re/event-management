package org.legendre.eventmanagement.host.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.host.model.Host;
import org.legendre.eventmanagement.host.model.repository.HostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;

    public Host createHost(Host request) {
        return hostRepository.save(
                new Host(request.getId(), request.getName(), request.getEmail(), request.getBio()));
    }

    public Optional<Host> getHostByName(String name) {
        return hostRepository.findByName(name);}

    public List<Host> getAll() {
        return hostRepository.findAll();
    }

    public Host updateHost(Host request, String name) {
        var findHost = hostRepository.findByName(name).orElse(null);

        assert findHost != null;
        findHost.setName(request.getName());
        findHost.setEmail(request.getEmail());
        findHost.setBio(request.getBio());
        return findHost;
    }

    public void deleteHost(String name) {
       hostRepository.findByName(name).ifPresent(hostRepository::delete);}
}
