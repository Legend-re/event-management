package org.legendre.eventmanagement.host.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legendre.eventmanagement.exception.DuplicateRecordException;
import org.legendre.eventmanagement.exception.ErrorCode;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.host.model.Host;
import org.legendre.eventmanagement.host.model.HostRequest;
import org.legendre.eventmanagement.host.model.repository.HostRepository;
import org.legendre.eventmanagement.host.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.exception.ErrorMessages.HOST_ALREADY_EXIST;
import static org.legendre.eventmanagement.exception.ErrorMessages.HOST_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    @Override
    public Host createHost(HostRequest request) {
        log.info("creating a host: {}", request.getEmail());
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {log.error("Host already exist: {}", request.getEmail());
                    throw new DuplicateRecordException(
                            new ErrorResponse(HOST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02)
                    );
                });

        var savedHost = hostRepository.save(
                Host.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .bio(request.getBio())
                        .build());
        log.info("guest created successfully: {}", savedHost.getName());
        return savedHost;
    }

    @Override
    public Optional<Host> getHostByName(String name) {
        log.info("Fetching host by name: {}", name);
        return Optional.ofNullable(hostRepository.findByName(name)
                .orElseThrow(() -> {log.error("Host not found: {}", name);
                       return new RecordNotFoundException(
                        new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01));}
                ));
    }

    @Override
    public List<Host> getAll() {
        log.info("Fetching all hosts");
        return hostRepository.findAll();
    }

    @Override
    public Host updateHost(HostRequest request, String email) {
        log.info("updating a host: {}", email);
        var findHost = hostRepository.findByEmail(email)
                .orElseThrow(
                        () -> {log.error("Host not found: {}", email);
                               return new RecordNotFoundException(
                                new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01));}
                );
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {log.error("Host already exist: {}", request.getEmail());
                    throw new DuplicateRecordException(
                            new ErrorResponse(HOST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        Host savedHost = hostRepository.save(
                findHost.toBuilder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .bio(request.getBio()).build());
        log.info("Host created successfully: {}", savedHost.getName());
        return savedHost;
    }

    @Override
    public void deleteHost(String name) {
        log.info("Deleting an host: {}", name);
        hostRepository.findByName(name).ifPresent(hostRepository::delete);
        log.info("Host deleted successfully: {}", name);
    }
}