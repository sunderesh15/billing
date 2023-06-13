package com.billing.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.billing.inventory.entity.Inventory;
import com.billing.inventory.repo.InventoryCreationRepository;
import com.billing.inventory.repo.InventoryRepository;
import com.billing.inventory.service.InventoryServiceImpl;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository repo;

    @Autowired
    private InventoryCreationRepository inventoryCreationRepository;

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @GetMapping("/item/{itemName}")
    public Page<Inventory> searchItems(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "D") String orderBy,
            @PathVariable String itemName) throws Exception {

        Pageable pageable = orderBy.equals("A") ? PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending())
                : PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        return repo.findAll(pageable);
    }

    @GetMapping("/item")
    public Page<Inventory> getAllItems(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "D") String orderBy
            ) throws Exception {

        Pageable pageable = orderBy.equals("A") ? PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending())
                : PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        return repo.findAll(pageable);
    }

    @PostMapping("/item")
    public String addItem(@RequestBody Inventory item) throws Exception {

        Optional<Inventory> itemObj = inventoryCreationRepository.findByItemName(item.getItemName());

        if (itemObj.isPresent()) {
            throw new Exception("item with " + item.getItemName() + "is present in the databse");
        } else {

            inventoryCreationRepository.save(item);
        }
        return "item created";

    }

    @PutMapping("/item")
    public String updateItem(@RequestBody Inventory item) throws Exception {

        Optional<Inventory> itemObj = inventoryCreationRepository.findByItemName(item.getItemName());

        if (itemObj.isPresent()) {
            inventoryCreationRepository.save(item);
        } else {

            throw new Exception("item with " + item.getItemName() + "is not present in the databse");
        }
        return "item created";

    }


    @PostMapping("/import")
	public void importItemFromExcelToDb(@RequestParam("file") List<MultipartFile> file) {

		inventoryServiceImpl.importToDb(file);

	}

}
