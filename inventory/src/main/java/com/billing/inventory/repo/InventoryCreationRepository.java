package com.billing.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billing.inventory.entity.Inventory;

@Repository
public interface InventoryCreationRepository extends JpaRepository<Inventory,Integer>{


    Optional<Inventory> findByItemName(String item);
    
}
