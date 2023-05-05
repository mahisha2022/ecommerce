package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Model.Retailer;
import com.revature.ecommerce.Service.ProductService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    /**
     * Add product
     * ENDPOINT localhost:9000/api/retailer/{retailerId}/products
     * @param product
     * @return
     */
    @PostMapping("/api/retailer/{retailerId}/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @PathVariable long retailerId,
                                             @RequestParam int quantity){
      try {
          productService.addProduct(product, retailerId, quantity);
          return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Successfully");
      }
      catch (InvalidInput e){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    }


    /**
     * Get Product By Id
     * ENDPOINT GET localhost:9000/api/ecommerce/product/{productId}
     *
     * @param productId
     * @return
     */
    @GetMapping("/api/ecommerce/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable long productId)  {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok().body(product);
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update Product By id
     * ENDPOINT PATCH localhost:9000/api/ecommerce/product/{productId}
     * @param productId
     * @param product
     * @return
     */

    @PatchMapping("/api/ecommerce/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable long productId, @RequestBody Product product){
        try {
            productService.updateProduct(productId, product);
            return ResponseEntity.ok().body("Product updated successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Delete Product By Id
     * ENDPOINT DELETE localhost:9000//api/ecommerce/product/{productId}
     * @param productId
     * @return
     */
    @DeleteMapping("/api/ecommerce/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().body("Product Deleted Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidInput e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
