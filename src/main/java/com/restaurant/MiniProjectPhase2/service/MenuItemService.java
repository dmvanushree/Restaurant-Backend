package com.restaurant.MiniProjectPhase2.service;

import com.restaurant.MiniProjectPhase2.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    List<MenuItem> getAllMenuItems();
    Optional<MenuItem> getMenuItemById(Long id);
    MenuItem addMenuItem(MenuItem menuItem);
    MenuItem createMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(Long id, MenuItem menuItem);
    void deleteMenuItem(Long id);

    // If you really need these extra methods, keep them, otherwise remove
    MenuItem create(MenuItem item);
    MenuItem findById(Long id);
    List<MenuItem> findAll();
    MenuItem update(Long id, MenuItem item);
    void delete(Long id);
}
