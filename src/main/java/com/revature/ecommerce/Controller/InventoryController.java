package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Repository.InventoryRepository;
import com.revature.ecommerce.Service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    /**
     * Get Inventory by Id
     * ENDPOINT localhost:9000/api/inventory/{inventoryId}
     * @param inventoryId
     * @return
     */

    @GetMapping("/api/inventory/{inventoryId}")
    public ResponseEntity<?> getInventoryById(@PathVariable long inventoryId){
        try {
           Inventory inventory =  inventoryService.getInventoryById(inventoryId);
            return ResponseEntity.ok().body(inventory);
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Update Inventory
     * ENDPOINT PATCH localhost:9000/api/inventory/{inventoryId}
     * @param inventoryId
     * @param inventory
     * @return
     */
    @PatchMapping("/api/inventory/{inventoryId}")
    public ResponseEntity<String> updateInventory(@PathVariable long inventoryId, @RequestBody Inventory inventory){
        try {
            inventoryService.updateInventory(inventoryId, inventory);
            return ResponseEntity.ok().body("Inventory updated Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete Inventory
     * ENDPOINT DELETE localhost:9000/api/inventory/{inventoryId}
     * @param inventoryId
     * @return
     */

    @DeleteMapping("/api/inventory/{inventoryId}")
    public ResponseEntity<?> deleteInventory(@PathVariable long inventoryId){
        try {
            inventoryService.deleteInventory(inventoryId);
            return ResponseEntity.ok().body("Inventory Deleted Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidInput e){
        return status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
