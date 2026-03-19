package com.example.bankingsystem.controller;

import com.example.bankingsystem.entity.Customer;
import com.example.bankingsystem.entity.dto.CustomerDto;
import com.example.bankingsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // POST: http://localhost:8080/api/customers
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto dto) {
        Customer createdCustomer = customerService.createCustomer(dto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // GET: http://localhost:8080/api/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // GET: http://localhost:8080/api/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // DELETE: http://localhost:8080/api/customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status
    }
}