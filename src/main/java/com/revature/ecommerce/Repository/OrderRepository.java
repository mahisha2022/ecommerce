package com.revature.ecommerce.Repository;

import com.revature.ecommerce.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUserId(long userId);
    
    @Query("SELECT o FROM Orders o INNER JOIN o.product p WHERE p.retailer.id = :id")
    List<Orders> findByProductRetailerId(long id);

    Optional<Orders> findByProductId(long productId);
}
