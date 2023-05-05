package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class InventoryService {


    @Autowired
     private InventoryRepository inventoryRepository;


    public Inventory getInventoryById(long inventoryId) throws InvalidInput {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(inventoryId);
        if (inventoryOptional.isPresent()){
            Inventory inventory = inventoryOptional.get();
            return inventory;
        }
        else {
            throw new InvalidInput("Inventory not found");
        }
    }
}
