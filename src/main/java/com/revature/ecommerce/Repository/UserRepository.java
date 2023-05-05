package com.revature.ecommerce.Repository;

import com.revature.ecommerce.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);


    AppUser findByEmailAndPassword(String email, String password);

    AppUser findByUserNameAndPassword(String userName, String password);
}
