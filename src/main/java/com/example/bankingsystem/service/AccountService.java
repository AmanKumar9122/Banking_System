package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.dto.AccountDto;

import java.util.List;

public interface AccountService {
    Account createAccount(Integer customerId, AccountDto dto);
    Account getAccount(Integer id);
    List<Account> getAccountsByCustomer(Integer customerId);
    void deactivateAccount(Integer id);
}
