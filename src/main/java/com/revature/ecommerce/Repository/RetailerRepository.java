package com.revature.ecommerce.Repository;

import com.revature.ecommerce.Model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    Retailer findByEmail(String email);

    Retailer findByEmailAndPassword(String email, String password);
}
