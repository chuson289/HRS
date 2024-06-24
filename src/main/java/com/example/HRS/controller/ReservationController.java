package com.example.HRS.controller;

import com.example.HRS.model.Reservation;
import com.example.HRS.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
/**
 * Controller class for managing Reservation-related HTTP requests.
 * Supports operations such as fetching all reservations, retrieving a reservation by ID,
 * creating, updating, and deleting reservations.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Retrieves all reservations.
     * @return A ResponseEntity containing a list of all reservations with an HTTP status of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * Retrieves a reservation by its ID.
     * @param id The ID of the reservation to retrieve.
     * @return A ResponseEntity containing the reservation if found (HTTP status 200 - OK),
     *         or ResponseEntity.notFound() if no reservation with the given ID exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new reservation.
     * @param reservation The reservation object to be created.
     * @return A CompletableFuture that resolves to the created Reservation object.
     */
    @PostMapping
    public CompletableFuture<Reservation> createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    /**
     * Updates an existing reservation.
     * @param id The ID of the reservation to update.
     * @param reservationDetails The updated details of the reservation.
     * @return A CompletableFuture that resolves to a ResponseEntity containing the updated Reservation object
     *         with an HTTP status of 200 (OK), or ResponseEntity.notFound() if no reservation with the given ID exists.
     */
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Reservation>> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        return reservationService.updateReservation(id, reservationDetails)
                .thenApply(ResponseEntity::ok);
    }

    /**
     * Deletes a reservation by its ID.
     * @param id The ID of the reservation to delete.
     * @return A CompletableFuture that resolves to a ResponseEntity with HTTP status 204 (No Content)
     *         upon successful deletion, or ResponseEntity.notFound() if no reservation with the given ID exists.
     */
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservation(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build());
    }
}