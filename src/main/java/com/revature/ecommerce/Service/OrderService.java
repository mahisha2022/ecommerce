package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.AppUser;
import com.revature.ecommerce.Model.Inventory;
import com.revature.ecommerce.Model.Orders;
import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Repository.InventoryRepository;
import com.revature.ecommerce.Repository.OrderRepository;
import com.revature.ecommerce.Repository.ProductRepository;
import com.revature.ecommerce.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Add order
     * @param orders
     * @return
     */
    public void  addOrder(long productId, long userId, Orders orders) throws InvalidInput {
        try {
            AppUser user = userRepository.findById(userId).orElseThrow(() -> new InvalidInput("User Not Found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new InvalidInput("Product not found"));
            Inventory inventory = inventoryRepository.findByProduct(product);

            LocalDate today = LocalDate.now();
            int orderQty = orders.getQuantity();
            if (inventory.getQuantity() < orderQty){
                throw new InvalidInput("Not enough quantity available");
            }


            orders.setDate(today);
            orders.setTotalPrice(orders.getQuantity() * product.getPrice());
            orders.setProduct(product);
            orders.setUser(user);

            inventory.setQuantity(inventory.getQuantity() - orderQty);

            orderRepository.save(orders);
            inventoryRepository.save(inventory);
        }
       catch (InvalidInput e){
            throw new InvalidInput("Failed to add order");
        }

    }

    /**
     * Delete Order By Id
     * @param orderId
     * @throws InvalidInput
     */

    public void deleteOrder(long orderId) throws InvalidInput {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        Orders orders = orderOptional.get();
        if (orderOptional.isPresent()){
            orderRepository.delete(orders);
        }
        else {
            throw new InvalidInput("Order not found");
        }
    }

    /**
     * Update Order
     * @param orderId
     * @param updatedOrders
     * @throws InvalidInput
     */

    public void updateOrder(long orderId, Orders updatedOrders) throws InvalidInput {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        Orders orders = orderOptional.get();
        if (orderOptional.isPresent()){
            if (updatedOrders.getQuantity() != 0){
                orders.setQuantity(updatedOrders.getQuantity());
            }
            if (updatedOrders.getProduct() != null){
                orders.setProduct(updatedOrders.getProduct());
            }
            orderRepository.save(orders);
        }
        else {
            throw new InvalidInput("Failed to update order");
        }
    }


    /**
     * Get Order By Id
     * @param orderId
     * @return
     * @throws InvalidInput
     */
    public Orders getOrderById(long orderId) throws InvalidInput {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        Orders orders = orderOptional.get();
        if (orderOptional.isPresent()){
            return orders;
        }
        else {
            throw new InvalidInput("Order not found");
        }
    }

    /**
     * Get Order by user
     * @param userId
     * @return
     */

    public List<Orders> getOrderByUser(long userId){
        return orderRepository.findByUserId(userId);
    }

    /**
     * Get Order By Retailer
     * @param retailerId
     * @return
     */
    public List<Orders> getOrderByRetailer(long retailerId){
        return orderRepository.findByProductRetailerId(retailerId);
    }

    /**
     * Get order By Order
     * @param productId
     * @return
     */
    public Optional<Orders> getOrderByProduct(long productId){
      return orderRepository.findByProductId(productId);
    }

}
