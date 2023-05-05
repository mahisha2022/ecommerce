package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.AppUser;
import com.revature.ecommerce.Model.Orders;
import com.revature.ecommerce.Service.OrderService;
import com.revature.ecommerce.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * Add Order
     * EndPOINT localhost:9000/api/order/{productId}/user/{userId}
     * @param productId
     * @param orders
     * @return
     */
    @PostMapping("/api/order/{productId}/user/{userId}")
    public ResponseEntity<String> addOrder(@PathVariable long productId, @PathVariable long userId, @RequestBody Orders orders){
        try {
            orderService.addOrder(productId,userId, orders);
            return ResponseEntity.ok().body("Order Added Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get Order By Id
     * ENDPOINT GET localhost:9000/api/order/{productId}
     *
     * @param orderId
     * @return
     */

    @GetMapping("/api/order/{orderId}")
    public Optional<Orders> getOrderById(@PathVariable long orderId) throws InvalidInput {
     return orderService.getOrderById(orderId);
    }

    /**
     * Get order by User
     * ENDPOINT localhost:9000/api/order/user/{userId}
     * @param userId
     * @return
     */
    @GetMapping("/api/order/user/{userId}")
    public List<Orders> getOrderByUser(@PathVariable long userId ) throws InvalidInput {
        AppUser user = userService.getUserById(userId);
        return orderService.getOrderByUser(user);
    }

    /**
     * Get Order By Product
     * ENDPOINT GET localhost:9000/api/order/product/{productId}
     * @param productId
     * @return
     */
    @GetMapping("/api/order/product/{productId}")
    public Optional<Orders> getOrdersByProduct(@PathVariable long productId){
        return orderService.getOrderByProduct(productId);
    }

    /**
     * Get Order By Retailer
     * ENDPOINT GET localhost:9000/api/order/retailer/{retailerId}
     * @param retailerId
     * @return
     */

    @GetMapping("/api/order/retailer/{retailerId}")
    public List<Orders> getOrdersByRetailer(@PathVariable long retailerId){
        return orderService.getOrderByRetailer(retailerId);
    }

    /**
     * Delete Order By Id
     * ENDPOINT DELETE localhost:9000/api/order/{orderId}
     * @param orderId
     * @return
     */
    @DeleteMapping("/api/order/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable long orderId){
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body("Order Deleted Successfully");
        }
        catch (InvalidInput e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    /**
     * Update Order By Id
     * ENDPOINT localhost:9000/api/order/{orderId}
     * @param orderId
     * @param orders
     * @return
     */

    @PatchMapping("/api/order/{orderId}")
    public ResponseEntity<String> updateOrderById(@PathVariable long orderId, @RequestBody Orders orders){
        try {
            orderService.updateOrder(orderId, orders);
            return ResponseEntity.ok().body("Order Updated Successfully");
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
