package com.restaurant.MiniProjectPhase2.service.impl;

import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.MenuItem;
import com.restaurant.MiniProjectPhase2.repository.MenuItemRepository;
import com.restaurant.MiniProjectPhase2.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repo;

    public MenuItemServiceImpl(MenuItemRepository repo) {
        this.repo = repo;
    }

    // ----- First set -----
    @Override
    public List<MenuItem> getAllMenuItems() {
        return repo.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemById(Long id) {
        return repo.findById(id);
    }

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return repo.save(menuItem);
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return repo.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + id));
        existing.setName(menuItem.getName());
        existing.setPrice(menuItem.getPrice());
        existing.setDescription(menuItem.getDescription());
        return repo.save(existing);
    }

    @Override
    public void deleteMenuItem(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found: " + id);
        }
        repo.deleteById(id);
    }

    // ----- Second set -----
    @Override
    public MenuItem create(MenuItem item) {
        return repo.save(item);
    }

    @Override
    public MenuItem findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + id));
    }

    @Override
    public List<MenuItem> findAll() {
        return repo.findAll();
    }

    @Override
    public MenuItem update(Long id, MenuItem item) {
        MenuItem existing = findById(id);
        existing.setName(item.getName());
        existing.setPrice(item.getPrice());
        existing.setDescription(item.getDescription());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found: " + id);
        }
        repo.deleteById(id);
    }
}
