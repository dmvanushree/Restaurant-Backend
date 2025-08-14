package com.restaurant.MiniProjectPhase2.repository;

import com.restaurant.MiniProjectPhase2.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {}
