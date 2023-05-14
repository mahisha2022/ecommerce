package com.revature.ecommerce.Repository;

import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRetailer(Retailer retailer);
}
