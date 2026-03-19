package com.example.bankingsystem.repository;

import com.example.bankingsystem.entity.Loan;
import com.example.bankingsystem.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByAccountId(Integer accountId);
    List<Loan> findByStatus(LoanStatus status);
    Optional<Loan> findByLoanId(String loanId);
}
