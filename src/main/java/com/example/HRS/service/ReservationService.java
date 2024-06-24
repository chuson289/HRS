package com.example.HRS.service;

import com.example.HRS.Repository.ReservationRepository;
import com.example.HRS.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Cacheable(value = "reservationCache", key = "#id", unless = "#result.isEmpty()")
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Async
    public CompletableFuture<Reservation> createReservation(Reservation reservation) {
        int latestRoomNumber = reservationRepository.findTopByOrderByRoomNumberDesc()
                .map(Reservation::getRoomNumber)
                .orElse(100); // Default starting room number
        reservation.setRoomNumber(latestRoomNumber + 1);
        return CompletableFuture.completedFuture(reservationRepository.save(reservation));
    }

    @Async
    @CacheEvict(value = "reservationCache", allEntries = true)
    public CompletableFuture<Void> deleteReservation(Long id) {
        reservationRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Reservation> updateReservation(Long id, Reservation reservationDetails) {
        return CompletableFuture.completedFuture(reservationRepository.findById(id).map(reservation -> {
            reservation.setGuestName(reservationDetails.getGuestName());
            reservation.setCheckInDate(reservationDetails.getCheckInDate());
            reservation.setCheckOutDate(reservationDetails.getCheckOutDate());
            reservation.setRoomNumber(reservationDetails.getRoomNumber());
            reservation.setHotel(reservationDetails.getHotel());
            return reservationRepository.save(reservation);
        }).orElseGet(() -> {
            reservationDetails.setId(id);
            return reservationRepository.save(reservationDetails);
        }));
    }
}
