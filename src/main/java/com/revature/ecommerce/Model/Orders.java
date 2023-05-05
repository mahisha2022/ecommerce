package com.revature.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private int quantity;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser user;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;





}
