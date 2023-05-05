package com.revature.ecommerce;

import com.revature.ecommerce.Exception.InvalidInput;
import com.revature.ecommerce.Model.AppUser;
import com.revature.ecommerce.Model.Orders;
import com.revature.ecommerce.Model.Product;
import com.revature.ecommerce.Model.Retailer;
import com.revature.ecommerce.Repository.OrderRepository;
import com.revature.ecommerce.Repository.ProductRepository;
import com.revature.ecommerce.Repository.RetailerRepository;
import com.revature.ecommerce.Repository.UserRepository;
import com.revature.ecommerce.Service.OrderService;
import com.revature.ecommerce.Service.ProductService;
import com.revature.ecommerce.Service.RetailerService;
import com.revature.ecommerce.Service.UserService;
import jakarta.annotation.security.RunAs;
import org.assertj.core.api.AbstractInstantAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)

class EcommerceApplicationTests {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private RetailerService retailerService;
	@Mock
	private RetailerRepository retailerRepository;
	@InjectMocks
	private ProductService productService;
	@Mock
	private ProductRepository productRepository;



	@Test
	public void userSignUpTest() throws InvalidInput {
		AppUser user = new AppUser();
		user.setFirstName("Dan");
		user.setLastName("Maluk");
		user.setEmail("danmaluk@gmail.com");
		user.setPassword("1234pass");

		// return the user
		when(userRepository.save(user)).thenReturn(user);
//		verify(userRepository, times(1)).save(user);
		assertEquals(user, userService.userSignUp(user));

	}

	@Test
	public void retailerSignUp() throws InvalidInput{
		Retailer retailer = new Retailer();
		retailer.setFirstName("Name");
		retailer.setLastName("Last");
		retailer.setPhone("123-456-7891");
		retailer.setEmail("retailer@user.com");
		retailer.setPassword("1234pass");
		when(retailerRepository.save(retailer)).thenReturn(retailer);
		assertEquals(retailer, retailerService.retailSignup(retailer));
	}

//	@Test
//	public void addProduct() throws InvalidInput {
//		Product product = new Product();
//		Optional<Retailer> retailerOptional = retailerRepository.findById(product.getRetailerId());
//		Retailer retailer = retailerOptional.get();
//		int qty = 10;
//		product.setName("Cloth");
//		product.setPrice(15.99);
//		product.setRetailer(retailer);
//		product.setDescription("nice and cool");
//
//		when(productRepository.save(product)).thenReturn(product);
//		verify(productRepository, times(1)).save(product);
//
//	}





}
