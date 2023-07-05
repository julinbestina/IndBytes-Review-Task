package com.indbytes.service;

import com.indbytes.dto.LoginDTO;
import com.indbytes.dto.SignUpDTO;
import com.indbytes.model.Customer;

public interface ICustomerService {
	public Customer convertCusDtoToEntity(SignUpDTO loginCustomer);

	public Customer signUp(Customer customer);

	public String Login(LoginDTO loginCustomer);

}
