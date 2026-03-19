package com.example.bankingsystem.repository;

import com.example.bankingsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerId(Integer customerId);
}

