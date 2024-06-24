package com.example.HRS.Repository;

import com.example.HRS.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findTopByOrderByRoomNumberDesc();
}
