package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.MenuItemController;
import com.restaurant.MiniProjectPhase2.model.MenuItem;
import com.restaurant.MiniProjectPhase2.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MenuItemControllerTest {

    @InjectMocks
    private MenuItemController controller;

    @Mock
    private MenuItemService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenuItem_success() {
        MenuItem item = new MenuItem();
        item.setId(1L);

        when(service.create(any())).thenReturn(item);

        ResponseEntity<MenuItem> response = controller.create(item);
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getMenuItemById_success() {
        MenuItem item = new MenuItem();
        item.setId(5L);

        when(service.findById(5L)).thenReturn(item);

        MenuItem result = controller.one(5L);
        assertEquals(5L, result.getId());
    }

    @Test
    void getAllMenuItems_success() {
        when(service.findAll()).thenReturn(List.of(new MenuItem(), new MenuItem()));
        List<MenuItem> list = controller.all();
        assertEquals(2, list.size());
    }

    @Test
    void updateMenuItem_success() {
        MenuItem item = new MenuItem();
        item.setId(7L);

        when(service.update(eq(7L), any())).thenReturn(item);

        MenuItem result = controller.update(7L, item);
        assertEquals(7L, result.getId());
    }

    @Test
    void deleteMenuItem_success() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
