package com.example.bankingsystem.repository;

import com.example.bankingsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerId(Integer customerId);

    // Derived Queries
    // list of account type current and balance more than 10000
    // List<Account> findByTypeAndBalanceGreaterThan(String type, double balance);

    // JPQL Queries
    // list of account type current and balance more than 10000
    // @Query("SELECT a FROM Account a WHERE a.type = :type AND a.balance > :balance")
    // List<Account> findByTypeAndBalanceGreaterThan(@Param("type") String type, @Param("balance") double balance);

    // Native Queries
    // list of account type current and balance more than 10000
    // @Query(value = "SELECT * FROM accounts WHERE type = :type AND balance > :balance", nativeQuery = true)
    // List<Account> findByTypeAndBalanceGreaterThan(@Param("type") String type, @Param("balance") double balance);

}

