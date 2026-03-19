package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction deposit(Integer accountId, double amount);
    Transaction withdraw(Integer accountId, double amount);
    void transfer(Integer fromId, Integer toId, double amount);
    List<Transaction> getMiniStatement(Integer accountId);
}
