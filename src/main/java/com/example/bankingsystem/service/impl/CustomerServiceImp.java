package com.example.bankingsystem.service.impl;

import com.example.bankingsystem.entity.Customer;
import com.example.bankingsystem.entity.dto.CustomerDto;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.CustomerRepository;
import com.example.bankingsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer createCustomer(CustomerDto dto){
        Customer customer = modelMapper.map(dto, Customer.class);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found with id:" + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Integer id) {
        if(!customerRepository.existsById(id)){
            throw new ResourceNotFoundException("Cannot delete: Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
