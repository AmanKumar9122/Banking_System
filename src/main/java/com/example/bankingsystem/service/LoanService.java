package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.Loan;
import com.example.bankingsystem.entity.dto.LoanRequestDto;

import java.util.List;

public interface LoanService {
    Loan applyLoan(LoanRequestDto dto);//XML Create
    List<Loan> getLoansByAccount(Integer accountId);
    List<Loan> getApprovedLoans();
}
