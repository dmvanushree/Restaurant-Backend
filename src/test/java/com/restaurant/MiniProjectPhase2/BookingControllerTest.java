package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.BookingController;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @InjectMocks
    private BookingController controller;

    @Mock
    private BookingService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_success() {
        TableBooking booking = new TableBooking();
        booking.setId(1L);

        when(service.createBooking(any())).thenReturn(booking);

        var result = controller.create(booking);
        assertEquals(1L, ((TableBooking) result).getId()); // Cast because controller.create() returns Object in your code
    }

    @Test
    void getBookingById_success() {
        TableBooking booking = new TableBooking();
        booking.setId(5L);

        when(service.getBookingById(5L)).thenReturn(booking);

        TableBooking result = controller.one(5L);
        assertEquals(5L, result.getId());
    }

    @Test
    void getAllBookings_success() {
        when(service.getAllBookings()).thenReturn(List.of(new TableBooking(), new TableBooking()));

        List<TableBooking> list = controller.all();
        assertEquals(2, list.size());
    }

    @Test
    void updateBooking_success() {
        TableBooking booking = new TableBooking();
        booking.setId(7L);

        when(service.updateBooking(eq(7L), any())).thenReturn(booking);

        TableBooking result = controller.update(7L, booking);
        assertEquals(7L, result.getId());
    }
}
