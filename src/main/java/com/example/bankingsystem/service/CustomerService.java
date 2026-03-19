package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.Customer;
import com.example.bankingsystem.entity.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto dto);
    Customer getCustomerById(Integer id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Integer id);
}
