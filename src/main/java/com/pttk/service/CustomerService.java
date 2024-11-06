package com.pttk.service;

import com.pttk.model.dto.CustomerDto;
import com.pttk.model.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Customer findByUsername(String name);

    Customer save(CustomerDto customerDto);

    CustomerDto getCustomer(String username);

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);
}
