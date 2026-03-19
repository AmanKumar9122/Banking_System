package com.example.bankingsystem.service.impl;

import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.Loan;
import com.example.bankingsystem.entity.LoanStatus;
import com.example.bankingsystem.entity.dto.LoanRequestDto;
import com.example.bankingsystem.exception.InvalidOperationException;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.AccountRepository;
import com.example.bankingsystem.repository.LoanRepository;
import com.example.bankingsystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Loan applyLoan(LoanRequestDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + dto.getAccountId()));

        if (!account.isActive()) {
            throw new InvalidOperationException("Cannot apply for loan: Account is inactive.");
        }

        List<Loan> activeLoans = account.getLoans().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.APPROVED || loan.getStatus() == LoanStatus.ACTIVE)
                .toList();

        if (activeLoans.size() >= 3) {
            throw new InvalidOperationException("Cannot apply: Maximum of 3 active loans reached.");
        }

        double currentTotalLoanAmount = activeLoans.stream()
                .mapToDouble(Loan::getAmount)
                .sum();

        double maxAllowedLoan = account.getBalance() * 5;

        if ((currentTotalLoanAmount + dto.getAmount()) > maxAllowedLoan) {
            throw new InvalidOperationException("Cannot apply: Total loan amount exceeds 5x current balance. Max allowed: " + maxAllowedLoan);
        }

        Loan loan = modelMapper.map(dto, Loan.class);
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setStatus(LoanStatus.PENDING);
        loan.setAccount(account);

        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoansByAccount(Integer accountId) {
        if(!accountRepository.existsById(accountId)){
            throw new ResourceNotFoundException("Account not found with ID: " + accountId);
        }
        return loanRepository.findByAccountId(accountId);
    }

    @Override
    public List<Loan> getApprovedLoans() {
        return loanRepository.findByStatus(LoanStatus.APPROVED);
    }

    @Override
    public Loan updateLoanStatus(String loanId, LoanStatus status) {
        // Notice we use findByLoanId here instead of findById!
        Loan loan = loanRepository.findByLoanId(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

        loan.setStatus(status);
        return loanRepository.save(loan);
    }
}
