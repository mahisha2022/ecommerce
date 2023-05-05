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
import org.springframework.data.domain.Sort;
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
    public Orders  addOrder(long productId, long userId, Orders orders) throws InvalidInput {
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

            Orders newOrders = orderRepository.save(orders);
            newOrders.setProductId(productId);
            newOrders.setUserId(userId);

            inventory.setQuantity(inventory.getQuantity() - orderQty);
            inventoryRepository.save(inventory);
            return newOrders;
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

        Product product = orders.getProduct();
        Inventory inventory = inventoryRepository.findByProduct(product);

        if (orderOptional.isPresent()){
            if (updatedOrders.getQuantity() > 0){
                //get the quantity difference
                int oldQty = orders.getQuantity();
                int newQty = updatedOrders.getQuantity();
                int difference = newQty - oldQty;

                if (inventory.getQuantity() + difference  < 0){
                    throw new InvalidInput("Not enough quantity available");
                }



                orders.setQuantity(updatedOrders.getQuantity());
                orders.setTotalPrice(newQty * product.getPrice());

                inventory.setQuantity(inventory.getQuantity() - newQty + oldQty);
                inventoryRepository.save(inventory);

                 orderRepository.save(orders);


            }


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
    public Optional<Orders> getOrderById(long orderId) {
       return orderRepository.findById(orderId);

    }

    /**
     * Get Order by user
     * @param user
     * @return
     */

    public List<Orders> getOrderByUser(AppUser user) throws InvalidInput {
        Optional<AppUser> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            return orderRepository.findByUser(user);
        }
        else {
            throw new InvalidInput("User not found");
        }

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
