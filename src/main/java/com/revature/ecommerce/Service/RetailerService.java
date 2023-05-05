package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Retailer;
import com.revature.ecommerce.Repository.RetailerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RetailerService {

    @Autowired
    private RetailerRepository retailerRepository;


    /**
     * Retailer Signup
     * @param retailer
     * @throws InvalidInput
     */
    public void retailSignup(Retailer retailer) throws InvalidInput {
        Retailer existedRetailer = retailerRepository.findByEmail(retailer.getEmail());
        if (existedRetailer != null){
            throw  new InvalidInput("user already existed.");
        }
        if (retailer.getPassword().length() < 6){
            throw  new InvalidInput("Password must be at least 6 characters");
        }
        retailerRepository.save(retailer);
    }

    /**
     * Retailer Login
     * @param retailer
     * @return
     * @throws InvalidInput
     */

    public Retailer retailerLogin(Retailer retailer) throws InvalidInput{
        Retailer existedRetailer = retailerRepository.findByEmailAndPassword(retailer.getEmail(), retailer.getPassword());
        if (existedRetailer == null){
            throw new InvalidInput("User not found");
        }
        return retailer;
    }

    /**
     * Update Retailer
     * @param id
     * @param updatedRetailer
     */

    public void updateRetailer(Long id, Retailer updatedRetailer) throws InvalidInput {
        Optional<Retailer> retailerOptional = retailerRepository.findById(id);
        Retailer retailer = retailerOptional.get();
        if (retailerOptional.isPresent()){
            if (updatedRetailer.getFirstName() != null){
                retailer.setFirstName(updatedRetailer.getFirstName());
        }
            if (updatedRetailer.getLastName() != null){
                retailer.setLastName(updatedRetailer.getLastName());
            }
            if (updatedRetailer.getAddress() != null){
                retailer.setAddress(updatedRetailer.getAddress());
            }
            if (updatedRetailer.getPhone() != null){
                retailer.setPhone(updatedRetailer.getPhone());
            }
            if (updatedRetailer.getEmail() != null){
                retailer.setEmail(updatedRetailer.getEmail());
            }
            if (updatedRetailer.getPassword() != null){
                retailer.setPassword(updatedRetailer.getPassword());
            }

            retailerRepository.save(retailer);
        }
        else {
            throw new InvalidInput("Failed to update Retailer profile");
        }

    }

    /**
     * Get Retailer By Id
     * @param retailerId
     * @return
     */

    public Optional<Retailer> getRetailerById(Long retailerId){
        return retailerRepository.findById(retailerId);
    }



}
