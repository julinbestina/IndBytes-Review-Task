package com.indbytes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indbytes.dto.LoginDTO;
import com.indbytes.dto.SignUpDTO;
import com.indbytes.model.Customer;
import com.indbytes.service.ICustomerService;

@RestController
@RequestMapping("/bookstore")
public class CustomerController {

	@Autowired
	ICustomerService customerService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpCustomer) {
		Customer customer = customerService.convertCusDtoToEntity(signUpCustomer);
		customerService.signUp(customer);
		return ResponseEntity.status(HttpStatus.OK).body("Account created Successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginCustomer) {
		String token = customerService.Login(loginCustomer);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

}
