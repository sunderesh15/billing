package com.billing.inventory.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.billing.inventory.entity.Inventory;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory,Integer>{


    Page<Inventory> findAll(Pageable paging);

    Page<Inventory>findByItemName(Pageable paging, String item);
    
}
