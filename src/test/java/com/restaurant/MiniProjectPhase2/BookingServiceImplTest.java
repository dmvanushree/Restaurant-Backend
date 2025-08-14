package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.model.TableBooking;
import com.restaurant.MiniProjectPhase2.repository.BookingRepository;
import com.restaurant.MiniProjectPhase2.service.BookingService;
import com.restaurant.MiniProjectPhase2.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl service;

    @Mock
    private BookingRepository repo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_success() {
        TableBooking booking = new TableBooking();
        booking.setCustomerName("Alice");

        when(repo.save(booking)).thenReturn(booking);

        TableBooking result = service.createBooking(booking);
        assertEquals("Alice", result.getCustomerName());
    }

    @Test
    void getBookingById_found() {
        TableBooking booking = new TableBooking();
        booking.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(booking));

        TableBooking result = service.getBookingById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllBookings_success() {
        when(repo.findAll()).thenReturn(List.of(new TableBooking(), new TableBooking()));
        List<TableBooking> list = service.getAllBookings();
        assertEquals(2, list.size());
    }

    @Test
    void updateBooking_success() {
        TableBooking existing = new TableBooking();
        existing.setId(5L);
        existing.setCustomerName("Old");

        TableBooking updated = new TableBooking();
        updated.setCustomerName("New");

        when(repo.findById(5L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        TableBooking result = service.updateBooking(5L, updated);
        assertEquals("New", result.getCustomerName());
    }

    @Test
    void deleteBooking_success() {
        doNothing().when(repo).deleteById(1L);
        service.deleteBooking(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
