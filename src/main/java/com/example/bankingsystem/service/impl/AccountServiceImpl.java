package com.example.bankingsystem.service.impl;

import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.Customer;
import com.example.bankingsystem.entity.dto.AccountDto;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.AccountRepository;
import com.example.bankingsystem.repository.CustomerRepository;
import com.example.bankingsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Account createAccount(Integer customerId, AccountDto dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create account: Customer not found with ID: " + customerId));

        Account account = modelMapper.map(dto,Account.class);

        account.setBalance(dto.getInitialDeposit());
        account.setCustomer(customer);
        account.setActive(true);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found with ID: " + id));
    }

    @Override
    public List<Account> getAccountsByCustomer(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public void deactivateAccount(Integer id) {
    Account account = getAccount(id);
    account.setActive(false);
    accountRepository.save(account);
    }
}
