package com.example.bankingsystem.service.impl;

import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.TransactionType;
import com.example.bankingsystem.exception.InsufficientBalanceException;
import com.example.bankingsystem.exception.InvalidOperationException;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.AccountRepository;
import com.example.bankingsystem.repository.TransactionRepository;
import com.example.bankingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Transaction deposit(Integer accountId, double amount) {
        if (amount <= 0) throw new InvalidOperationException("Deposit amount must be greater than zero.");

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (!account.isActive()) throw new InvalidOperationException("Cannot deposit: Account is inactive.");

        // Update balance
        account.setBalance(account.getBalance() + amount);

        // Record transaction
        Transaction txn = new Transaction();
        txn.setType(TransactionType.CREDIT);
        txn.setAmount(amount);
        txn.setDate(LocalDateTime.now());
        txn.setAccount(account);

        accountRepository.save(account);
        return transactionRepository.save(txn);
    }

    @Override
    @Transactional
    public Transaction withdraw(Integer accountId, double amount) {
        if (amount <= 0) throw new InvalidOperationException("Withdrawal amount must be greater than zero.");

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (!account.isActive()) throw new InvalidOperationException("Cannot withdraw: Account is inactive.");
        if (account.getBalance() < amount) throw new InsufficientBalanceException("Insufficient balance for withdrawal.");

        // Update balance
        account.setBalance(account.getBalance() - amount);

        // Record transaction
        Transaction txn = new Transaction();
        txn.setType(TransactionType.DEBIT);
        txn.setAmount(amount);
        txn.setDate(LocalDateTime.now());
        txn.setAccount(account);

        accountRepository.save(account);
        return transactionRepository.save(txn);
    }

    @Override
    @Transactional
    public void transfer(Integer fromId, Integer toId, double amount) {
        if (amount <= 0) throw new InvalidOperationException("Transfer amount must be greater than zero.");
        if (fromId.equals(toId)) throw new InvalidOperationException("Cannot transfer to the same account.");

        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found."));
        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found."));

        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new InvalidOperationException("One or both accounts are inactive.");
        }
        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient funds to complete transfer.");
        }

        // 1. Deduct from source
        fromAccount.setBalance(fromAccount.getBalance() - amount);

        // 2. Add to destination
        toAccount.setBalance(toAccount.getBalance() + amount);

        // 3. Record transaction for source (Money Out = DEBIT)
        Transaction txnOut = new Transaction();
        txnOut.setType(TransactionType.DEBIT);
        txnOut.setAmount(amount);
        txnOut.setDate(LocalDateTime.now());
        txnOut.setAccount(fromAccount);

        // 4. Record transaction for destination (Money In = CREDIT)
        Transaction txnIn = new Transaction();
        txnIn.setType(TransactionType.CREDIT);
        txnIn.setAmount(amount);
        txnIn.setDate(LocalDateTime.now());
        txnIn.setAccount(toAccount);

        // Save the accounts and the newly created transactions
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transactionRepository.save(txnOut);
        transactionRepository.save(txnIn);
    }

    @Override
    public List<Transaction> getMiniStatement(Integer accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found.");
        }
        return transactionRepository.findTop10ByAccountIdOrderByDateDesc(accountId);
    }
}