package com.example.bankingsystem.controller;

import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // POST: http://localhost:8080/api/transactions/deposit?accountId=1&amount=500.00
    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestParam Integer accountId,
            @RequestParam double amount) {

        Transaction transaction = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(transaction);
    }

    // POST: http://localhost:8080/api/transactions/withdraw?accountId=1&amount=200.00
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestParam Integer accountId,
            @RequestParam double amount) {

        Transaction transaction = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(transaction);
    }

    // POST: http://localhost:8080/api/transactions/transfer?fromId=1&toId=2&amount=300.00
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam Integer fromId,
            @RequestParam Integer toId,
            @RequestParam double amount) {

        transactionService.transfer(fromId, toId, amount);
        return ResponseEntity.ok("Transfer of " + amount + " completed successfully.");
    }

    // GET: http://localhost:8080/api/transactions/1/statement
    @GetMapping("/{accountId}/statement")
    public ResponseEntity<List<Transaction>> getMiniStatement(@PathVariable Integer accountId) {
        List<Transaction> statement = transactionService.getMiniStatement(accountId);
        return ResponseEntity.ok(statement);
    }
}