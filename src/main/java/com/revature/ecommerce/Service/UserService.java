package com.revature.ecommerce.Service;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.AppUser;
import com.revature.ecommerce.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * User sign up with a unique email and a valid password
     * @param appUser
     * @return
     * @throws InvalidInput
     */
    public AppUser userSignUp(AppUser appUser) throws InvalidInput {
        AppUser existedUser = userRepository.findByEmail(appUser.getEmail());

        if (existedUser!= null ){
            throw new InvalidInput("email  already exists, use different email or login to existed account");
        }
        String password = appUser.getPassword();
        if (password.length() < 6){
            throw new InvalidInput("password must be at least 6 characters");
        }
            Random random = new Random();
            int id = random.nextInt(999);
            appUser.setUserName(appUser.getFirstName().substring(0, 2) + appUser.getLastName().substring(0, 2) + id);
            return userRepository.save(appUser);



    }

    /**
     * User Login with a valid username and password
     * @param email
     * @param password
     * @return
     * @throws InvalidInput
     */

    public AppUser useLogin(String email, String password) throws InvalidInput {
        AppUser appUser = userRepository.findByEmailAndPassword(email, password);
        AppUser userByUsername = userRepository.findByUserNameAndPassword(appUser.getUserName(), appUser.getPassword());
        if (appUser != null){
           return appUser;
        }
        else {
            throw new InvalidInput("Invalid Password or Username");
        }
    }

    /**
     * Get User By ID
     * @param userId
     * @return
     */
    public AppUser getUserById(long userId) throws InvalidInput {
       Optional<AppUser> appUserOptional = userRepository.findById(userId);
       if (appUserOptional.isPresent()){
           AppUser appUser = appUserOptional.get();
           return appUser;
       }
       else {
           throw new InvalidInput("User not found");
       }
}

    /**
     *
     * @param userId
     * @param updatedUser
     * @return
     */
    public AppUser updateUser(long userId, AppUser updatedUser) throws InvalidInput {
        Optional<AppUser> appUserOptional = userRepository.findById(userId);
        AppUser appUser = appUserOptional.get();
        if (appUserOptional.isPresent()){
            if (updatedUser.getUserName() != null){
                appUser.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getFirstName() != null){
                appUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null){
                appUser.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getPassword() != null){
                appUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getEmail() != null){
                appUser.setEmail(updatedUser.getEmail());
            }
            return userRepository.save(appUser);
        }
        else {
            throw new InvalidInput("Failed to update user");
        }

}





}
