package com.revature.ecommerce.Controller;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.AppUser;
import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * Post new Users
     * ENDPOINT localhost:9000/api/ecommerce/signup
     * @param appUser
     * @return
     */
    @PostMapping("/api/ecommerce/signup")
    public ResponseEntity<String> signUp(@RequestBody AppUser appUser){
        try {
           userService.userSignUp(appUser);
            return ok().body("User registered successfully");
        }
        catch (InvalidInput e){
            return badRequest().body(e.getMessage());

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * User Login
     * ENDPOINT localhost:9000/api/ecommerce/login
     * @param appUser
     * @return
     * @throws InvalidInput
     */

    @PostMapping("/api/ecommerce/login")
    public ResponseEntity<?> login(@RequestBody AppUser appUser) throws InvalidInput {
        try {
            AppUser userLogin = userService.useLogin(appUser.getEmail(), appUser.getPassword());
            return ok(userLogin);
        }
        catch (InvalidInput e){
            return status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/api/ecommerce/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable long userId){
        try {
           AppUser user = userService.getUserById(userId);
            return ResponseEntity.ok().body(user);
        }
        catch (InvalidInput e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/api/ecommerce/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable long userId, @RequestBody AppUser appUser){
        try {
            userService.updateUser(userId, appUser);
            return ok().body("USer profile updated Successfully");
        }
        catch (InvalidInput e){
            return badRequest().body(e.getMessage());
        }
    }


    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidInput e){
        return status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
