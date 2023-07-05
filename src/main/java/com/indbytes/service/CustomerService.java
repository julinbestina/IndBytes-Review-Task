package com.indbytes.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.indbytes.dto.LoginDTO;
import com.indbytes.dto.SignUpDTO;
import com.indbytes.exception.CustomerException;
import com.indbytes.model.Customer;
import com.indbytes.repository.BookRepository;
import com.indbytes.repository.CustomerRepository;
import com.indbytes.utils.JWTUtil;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JWTUtil jwtUtil;

	public Customer convertCusDtoToEntity(SignUpDTO signUpCustomer) {
		Customer customer = new Customer();
		customer = modelMapper.map(signUpCustomer, Customer.class);
		return customer;
	}

	public Customer signUp(Customer customer) {

		customerRepo.findByEmailId(customer.getEmailId()).ifPresent((existingCus) -> {
			throw new CustomerException("Account already exists");
		});
		return customerRepo.save(customer);
	}

	public String Login(LoginDTO loginCustomer) {

		customerRepo.findByEmailIdAndByPassword(loginCustomer.getEmailId(), loginCustomer.getPassword())
				.orElseThrow(() -> new CustomerException("Incorrect email or password"));
		return jwtUtil.generateJWT(loginCustomer);
	}

}
