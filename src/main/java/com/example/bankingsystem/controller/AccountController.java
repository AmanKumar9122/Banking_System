package com.example.bankingsystem.controller;

import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.dto.AccountDto;
import com.example.bankingsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // POST: http://localhost:8080/api/accounts/customer/1
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Account> createAccount(
            @PathVariable Integer customerId,
            @RequestBody AccountDto dto) {

        Account createdAccount = accountService.createAccount(customerId, dto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // GET: http://localhost:8080/api/accounts/5
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Integer id) {
        Account account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    // GET: http://localhost:8080/api/accounts/customer/1
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable Integer customerId) {
        List<Account> accounts = accountService.getAccountsByCustomer(customerId);
        return ResponseEntity.ok(accounts);
    }

    // PATCH: http://localhost:8080/api/accounts/5/deactivate
    // We use PATCH because we are only partially updating the account (changing isActive to false)
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        accountService.deactivateAccount(id);
        return ResponseEntity.ok("Account deactivated successfully.");
    }
}