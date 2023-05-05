package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Orders;
import com.revature.ecommerce.Service.OrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * Add Order
     * @param productId
     * @param orders
     * @return
     */
    @PostMapping("api/order/{productId}/user/{userId}")
    public ResponseEntity<String> addOrder(@PathVariable long productId, @PathVariable long userId, @RequestBody Orders orders){
        try {
            orderService.addOrder(productId,userId, orders);
            return ResponseEntity.ok().body("Order Added Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("api/ecommerce/order/{productId}")
    public ResponseEntity<?> getOrderById(@PathVariable long orderId){
        try {
            Orders orders = orderService.getOrderById(orderId);
            return ResponseEntity.ok().body(orders);
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
