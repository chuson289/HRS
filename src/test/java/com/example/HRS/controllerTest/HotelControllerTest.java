package com.example.HRS.controllerTest;


import com.example.HRS.model.Hotel;
import com.example.HRS.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HotelControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;
    @Autowired
    public HotelControllerTest(MockMvc mockMvc) {
        this.mockMvc  = mockMvc;
    }


    @Test
    void testGetAllHotels() throws Exception {
        List<Hotel> hotels = Arrays.asList(new Hotel(1L, "Hotel1", "address1","Destination1"), new Hotel(2L, "Hotel2", "address2","Destination2"));
        when(hotelService.getAllHotels()).thenReturn(CompletableFuture.completedFuture(hotels));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hotel1"))
                .andExpect(jsonPath("$[0].address").value("address1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Hotel2"))
                .andExpect(jsonPath("$[1].address").value("address2"));
    }

    @Test
    void testGetHotelsByDestination() throws Exception {
        hotelService.creatingHotel(new Hotel(1L, "Hotel1", "address1", "Destination1"));

        List<Hotel> hotels = List.of(new Hotel(1L, "Hotel1", "address1", "Destination1"));
        when(hotelService.getHotelsByDestination("address1")).thenReturn(hotels);

        mockMvc.perform(get("/api/hotels/search").param("destination", "Destination1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hotel1"))
                .andExpect(jsonPath("$[0].address").value("address1"));
    }

    @Test
    void testCreateHotel() throws Exception {
        Hotel hotel = new Hotel(1L, "Hotel1", "address1","Destination1");

        CompletableFuture<Hotel> future = CompletableFuture.completedFuture(hotel);
        when(hotelService.createHotel(any(Hotel.class))).thenReturn(future);

        mockMvc.perform(post("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hotel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hotel1"))
                .andExpect(jsonPath("$.address").value("address1"))
                .andExpect(jsonPath("$.destination").value("Destination1"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
