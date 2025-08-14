package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.TableBookingController;
import com.restaurant.MiniProjectPhase2.dto.BookingRequest;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.TableBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableBookingControllerTest {

    @InjectMocks
    private TableBookingController controller;

    @Mock
    private TableBookingService service;

    @Mock
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_success() {
        BookingRequest req = new BookingRequest("Alice", LocalDateTime.now(), 1, 2, null);
        TableBooking booking = new TableBooking();
        booking.setId(1L);
        when(service.create(any())).thenReturn(booking);

        var response = controller.create(req);
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void createBooking_withHandledByUser() {
        BookingRequest req = new BookingRequest("Alice", LocalDateTime.now(), 1, 2, 5L);
        TableBooking booking = new TableBooking();
        booking.setId(1L);

        User user = new User();
        user.setId(5L);

        when(userRepo.findById(5L)).thenReturn(Optional.of(user));
        when(service.create(any())).thenReturn(booking);

        var response = controller.create(req);
        assertEquals(1L, response.getBody().getId());
        verify(service).create(any());
    }

    @Test
    void createBooking_userNotFound() {
        BookingRequest req = new BookingRequest("Alice", LocalDateTime.now(), 1, 2, 5L);
        when(userRepo.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> controller.create(req));
    }

    @Test
    void getAllBookings() {
        when(service.findAll()).thenReturn(List.of(new TableBooking()));
        List<TableBooking> list = controller.all();
        assertEquals(1, list.size());
    }

    @Test
    void getBookingById() {
        TableBooking b = new TableBooking();
        b.setId(10L);
        when(service.findById(10L)).thenReturn(b);
        TableBooking result = controller.one(10L);
        assertEquals(10L, result.getId());
    }

    @Test
    void updateBooking_success() {
        BookingRequest req = new BookingRequest("Bob", LocalDateTime.now(), 2, 4, null);
        TableBooking updated = new TableBooking();
        updated.setId(5L);
        when(service.update(eq(5L), any())).thenReturn(updated);

        TableBooking result = controller.update(5L, req);
        assertEquals(5L, result.getId());
    }

    @Test
    void deleteBooking_success() {
        doNothing().when(service).delete(1L);
        var response = controller.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
