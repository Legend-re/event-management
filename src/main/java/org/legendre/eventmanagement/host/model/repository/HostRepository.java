package org.legendre.eventmanagement.host.model.repository;

import org.legendre.eventmanagement.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {
    Optional<Host> findByEmail(String email);
    Optional<Host> findByName(String name);
}
