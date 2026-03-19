package com.example.bankingsystem.controller;

import com.example.bankingsystem.entity.Loan;
import com.example.bankingsystem.entity.LoanStatus;
import com.example.bankingsystem.entity.dto.LoanRequestDto;
import com.example.bankingsystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // POST: http://localhost:8080/api/loans/apply
    // Accepts either JSON or XML payloads!
    @PostMapping(value = "/apply", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Loan> applyLoan(@RequestBody LoanRequestDto dto) {
        Loan loan = loanService.applyLoan(dto);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    // GET: http://localhost:8080/api/loans/account/1
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Loan>> getLoansByAccount(@PathVariable Integer accountId) {
        List<Loan> loans = loanService.getLoansByAccount(accountId);
        return ResponseEntity.ok(loans);
    }

    // GET: http://localhost:8080/api/loans/approved
    @GetMapping("/approved")
    public ResponseEntity<List<Loan>> getApprovedLoans() {
        List<Loan> approvedLoans = loanService.getApprovedLoans();
        return ResponseEntity.ok(approvedLoans);
    }

    @PatchMapping("/{loanId}/status")
    public ResponseEntity<Loan> updateLoanStatus(
            @PathVariable String loanId,
            @RequestParam LoanStatus status) {
        Loan updatedLoan = loanService.updateLoanStatus(loanId, status);
        return ResponseEntity.ok(updatedLoan);
    }
}