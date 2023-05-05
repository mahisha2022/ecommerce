package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.Retailer;
import com.revature.ecommerce.Service.RetailerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class RetailerController {

    @Autowired
    private RetailerService retailerService;

    /**
     * Retailer Signup
     * ENDPOINT POST localhost:9000/api/ecommerce/retailer/signup
     * @param retailer
     * @return
     */

    @PostMapping("/api/ecommerce/retailer/signup")
    public ResponseEntity<String> signup(@RequestBody Retailer retailer){
        try {
            retailerService.retailSignup(retailer);
            return ResponseEntity.ok().body("Retailer Added Successfully ");
        }
        catch (InvalidInput e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Retailer login
     * ENDPOINT POST localhost:9000/api/ecommerce/retailer/login
     * @param retailer
     * @return
     */

    @PostMapping("/api/ecommerce/retailer/login")
    public ResponseEntity<?> login(@RequestBody Retailer retailer){
        try {
            Retailer retailerLogin = retailerService.retailerLogin(retailer);
            return ResponseEntity.ok(retailerLogin);
        }
        catch (InvalidInput e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /**
     * Get Retailer By Id
     * ENDPOINT localhost:9000//api/ecommerce/retailer/{retailerId}
     * @param retailerId
     * @return
     * @throws InvalidInput
     */

    @GetMapping("/api/ecommerce/retailer/{retailerId}")
    public ResponseEntity<?> getRetailerById(@PathVariable long retailerId) throws InvalidInput {
        Optional<Retailer> retailer = retailerService.getRetailerById(retailerId);
        return  ResponseEntity.ok().body(retailer);

    }


    /**
     * Update Retailer
     * ENDPOINT PATCH localhost:9000/api/ecommerce/retailer/{retailerId}
     * @param retailerId
     * @param retailer
     * @return
     */
    @PatchMapping("/api/ecommerce/retailer/{retailerId}")
    public ResponseEntity<String> updateRetailer(@PathVariable long retailerId, @RequestBody Retailer retailer){
       try {
           retailerService.updateRetailer(retailerId, retailer);
           return ResponseEntity.ok().body("Retailer profile updated Successfully");
       }
       catch (InvalidInput e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }



    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidInput e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
