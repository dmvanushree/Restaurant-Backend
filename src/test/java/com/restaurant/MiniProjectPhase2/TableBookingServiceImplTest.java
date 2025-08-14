package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.TableBookingRepository;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.impl.TableBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableBookingServiceImplTest {

    @InjectMocks
    private TableBookingServiceImpl service;

    @Mock
    private TableBookingRepository bookingRepo;
    @Mock
    private UserRepository userRepo;

    private TableBooking booking;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new TableBooking();
        booking.setId(1L);
        booking.setCustomerName("Alice");
        booking.setTableNumber(2);
        booking.setBookingTime(LocalDateTime.now());

        user = new User();
        user.setId(10L);
    }

    @Test
    void create_success() {
        when(bookingRepo.existsByTableNumberAndBookingTime(2, booking.getBookingTime())).thenReturn(false);
        when(bookingRepo.save(booking)).thenReturn(booking);

        TableBooking result = service.create(booking);
        assertEquals("Alice", result.getCustomerName());
    }

    @Test
    void create_slotAlreadyBooked() {
        when(bookingRepo.existsByTableNumberAndBookingTime(2, booking.getBookingTime())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.create(booking));
    }

    @Test
    void findAll_success() {
        when(bookingRepo.findAll()).thenReturn(List.of(booking));
        List<TableBooking> list = service.findAll();
        assertEquals(1, list.size());
    }

    @Test
    void findById_found() {
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        TableBooking result = service.findById(1L);
        assertEquals("Alice", result.getCustomerName());
    }

    @Test
    void findById_notFound() {
        when(bookingRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void update_success() {
        TableBooking payload = new TableBooking();
        payload.setCustomerName("Bob");
        payload.setTableNumber(5);
        payload.setBookingTime(LocalDateTime.now());
        payload.setHandledBy(user);

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(userRepo.findById(10L)).thenReturn(Optional.of(user));
        when(bookingRepo.save(booking)).thenReturn(booking);

        TableBooking result = service.update(1L, payload);
        assertEquals("Bob", result.getCustomerName());
        assertEquals(user, result.getHandledBy());
    }

    @Test
    void delete_success() {
        doNothing().when(bookingRepo).deleteById(1L);
        service.delete(1L);
        verify(bookingRepo, times(1)).deleteById(1L);
    }
}
