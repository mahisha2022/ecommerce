package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Repository.InventoryRepository;
import com.revature.ecommerce.Service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/api/ecommerce/inventory/{inventoryId}")
    public ResponseEntity<?> getInventoryById(@PathVariable long inventoryId){
        try {
           Inventory inventory =  inventoryService.getInventoryById(inventoryId);
            return ResponseEntity.ok().body(inventory);
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
