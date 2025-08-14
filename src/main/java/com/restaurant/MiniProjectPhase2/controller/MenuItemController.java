package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.model.MenuItem;
import com.restaurant.MiniProjectPhase2.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService service;

    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    // Create
    @PostMapping
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem item) {
        MenuItem saved = service.create(item);
        return ResponseEntity.created(URI.create("/menu-items/" + saved.getId())).body(saved);
    }

    // Get by ID
    @GetMapping("/{id}")
    public MenuItem one(@PathVariable Long id) {
        return service.findById(id);
    }

    // Get all
    @GetMapping
    public List<MenuItem> all() {
        return service.findAll();
    }

    // Update
    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Long id, @RequestBody MenuItem item) {
        return service.update(id, item);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
