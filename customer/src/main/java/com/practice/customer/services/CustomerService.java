package com.practice.customer.services;

import com.practice.customer.dto.model.CustomerDto;
import com.practice.customer.dto.transfer.ResponseDto;
import com.practice.customer.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    CustomerDto registerCustomer(CustomerDto customerDto);
}
