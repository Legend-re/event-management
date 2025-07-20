package org.legendre.eventmanagement.guest.model.repository;

import org.legendre.eventmanagement.guest.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByEmailAddress(String email);
}
