package com.example.HRS.controller;

import com.example.HRS.model.Hotel;
import com.example.HRS.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
/**
 * Controller class for handling Hotel-related HTTP requests.
 * Supports operations such as fetching all hotels, searching hotels by destination,
 * and creating new hotels.
 */
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Retrieves all hotels asynchronously.
     * @return A CompletableFuture that resolves to a list of Hotel objects.
     */
    @GetMapping
    public CompletableFuture<List<Hotel>> getAllHotels() {
        return hotelService.getAllHotels();
    }

    /**
     * Searches hotels by destination.
     * @param destination The destination to search for.
     * @return A list of Hotel objects matching the specified destination.
     */
    @GetMapping("/search")
    public List<Hotel> getHotelsByDestination(@RequestParam String destination) {
        return hotelService.getHotelsByDestination(destination);
    }

    /**
     * Creates a new hotel.
     * @param hotel The hotel object to be created.
     * @return A CompletableFuture that resolves to a ResponseEntity containing the created Hotel object,
     *         with an HTTP status of 201 (Created).
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<Hotel>> createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel)
                .thenApply(savedHotel -> new ResponseEntity<>(savedHotel, HttpStatus.CREATED));
    }
}
