package org.legendre.eventmanagement.host.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.host.model.Host;
import org.legendre.eventmanagement.host.model.HostRequest;
import org.legendre.eventmanagement.host.model.repository.HostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;

    public Host createHost(HostRequest request) {
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {
                    throw new IllegalArgumentException("Email address for host already exists");
                });
        return hostRepository.save(
                Host.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .bio(request.getBio())
                        .build());
    }

    public Optional<Host> getHostByName(String name) {
        return Optional.ofNullable(hostRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Host not found")));
    }

    public List<Host> getAll() {
        return hostRepository.findAll();
    }

    public Host updateHost(HostRequest request, String email) {
        var findHost = hostRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("Email address for host does not exist")
                );
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {
                    throw new IllegalArgumentException("Email address already exist for another host");
                });

        return hostRepository.save(
                findHost.toBuilder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .bio(request.getBio()).build());
    }

    public void deleteHost(String name) {
        hostRepository.findByName(name).ifPresent(hostRepository::delete);
    }
}