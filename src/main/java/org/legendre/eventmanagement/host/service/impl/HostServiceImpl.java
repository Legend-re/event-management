package org.legendre.eventmanagement.host.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    @Override
    public Host createHost(HostRequest request) {
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(HOST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02)
                    );
                });

        return hostRepository.save(
                Host.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .bio(request.getBio())
                        .build());
    }

    @Override
    public Optional<Host> getHostByName(String name) {
        return Optional.ofNullable(hostRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01))
                ));
    }

    @Override
    public List<Host> getAll() {
        return hostRepository.findAll();
    }

    @Override
    public Host updateHost(HostRequest request, String email) {
        var findHost = hostRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RecordNotFoundException(
                                new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01))
                );
        hostRepository.findByEmail(request.getEmail())
                .ifPresent(host -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(HOST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        return hostRepository.save(
                findHost.toBuilder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .bio(request.getBio()).build());
    }

    @Override
    public void deleteHost(String name) {
        hostRepository.findByName(name).ifPresent(hostRepository::delete);
    }
}