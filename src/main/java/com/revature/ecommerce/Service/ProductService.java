package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Model.Retailer;
import com.revature.ecommerce.Repository.InventoryRepository;
import com.revature.ecommerce.Repository.ProductRepository;
import com.revature.ecommerce.Repository.RetailerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RetailerRepository retailerRepository;

    /**
     * Add product
     *
     * @param product
     */

    public void addProduct(Product product, long retailerId, int quantity) throws InvalidInput {
        Retailer retailer = retailerRepository.findById(retailerId).orElseThrow(() -> new InvalidInput("Retailer Not Found"));


        product.setRetailer(retailer);
        Product newProduct = productRepository.save(product);
        newProduct.setRetailerId(retailerId);
        productRepository.save(product);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);

    }


    /**
     * Update Product
     *
     * @param productId
     * @param updatedProduct
     */
    public void updateProduct(long productId, Product updatedProduct) throws InvalidInput {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.get();

        if (productOptional.isPresent()) {
            if (updatedProduct.getName() != null) {
                product.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                product.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getPrice() != 0.0) {
                product.setPrice(updatedProduct.getPrice());
            }
            productRepository.save(updatedProduct);
        } else {
            throw new InvalidInput("Failed to update product");
        }
    }

    /**
     * Delete product by Id
     *
     * @param productId
     */
    public void deleteProduct(long productId) throws InvalidInput {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.get();
        if (productOptional.isPresent()) {
            productRepository.delete(product);
        } else {
            throw new InvalidInput("Product do not exist");
        }
    }

    /**
     * Get product By Id
     *
     * @param productId
     * @return
     */

    public Optional<Product> getProductById(long productId)  {
       return productRepository.findById(productId);

    }

    /**
     * Get Product By Retailer
     * @param retailerId
     * @return
     * @throws InvalidInput
     */


    public List<Product> getProductsByRetailer(Long retailerId) throws InvalidInput {
        Retailer retailer = retailerRepository.findById(retailerId).orElse(null);
        List<Product> products = productRepository.findByRetailer(retailer);
        if (retailer != null) {
            return products;
        }
        else {
            return null;
        }

    }

    /**
     * Get List of All Products
     * @return
     */

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
