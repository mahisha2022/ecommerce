package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Repository.InventoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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


    /**
     * Get Inventory By Id
     * @param inventoryId
     * @return
     * @throws InvalidInput
     */

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


    /**
     *
     * @param inventoryId
     * @param updatedInv
     * @return
     */
    public Inventory updateInventory(long inventoryId, Inventory updatedInv) throws InvalidInput {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(inventoryId);
        if (inventoryOptional.isPresent()){
            Inventory inventory = inventoryOptional.get();
            if (updatedInv.getProduct() != null){
                inventory.setProduct(updatedInv.getProduct());
            }
            if (updatedInv.getQuantity()  != 0){
                inventory.setQuantity(updatedInv.getQuantity());
            }
            return inventoryRepository.save(inventory);
        }
        else {
            throw new InvalidInput("Failed to update Inventory");
        }
    }

    /***
     * Delete Inventory
     * @param inventoryId
     * @throws InvalidInput
     */

    public void deleteInventory(long inventoryId) throws InvalidInput {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(inventoryId);
        if (inventoryOptional.isPresent()){
            Inventory inventory = inventoryOptional.get();
            inventoryRepository.delete(inventory);
        }
        else {
            throw new InvalidInput("Failed to delete Inventory");
        }
    }


}
