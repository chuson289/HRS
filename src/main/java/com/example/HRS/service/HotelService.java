package com.example.HRS.service;

import com.example.HRS.Repository.HotelRepository;
import com.example.HRS.model.Hotel;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Executor taskExecutor;
    @Autowired
    public HotelService(HotelRepository hotelRepository, Executor taskExecutor) {
        this.hotelRepository = hotelRepository;
        this.taskExecutor = taskExecutor;
    }
    @Cacheable(value = "hotelsByDestinationCache", key = "#destination")
    public List<Hotel> getHotelsByDestination(String destination) {
        return hotelRepository.findByDestination(destination);
    }

    @Async
    public CompletableFuture<List<Hotel>> getAllHotels() {
        return CompletableFuture.supplyAsync(this::getHotels, taskExecutor);
    }

    @Cacheable(value = "allHotelsCache", key = "'allHotels'")
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Async
    public CompletableFuture<Hotel> createHotel(Hotel hotel) {
        return CompletableFuture.supplyAsync(() -> creatingHotel(hotel), taskExecutor);
    }

    @CachePut(value = "hotelsCache", key = "#hotel.id")
    public Hotel creatingHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
