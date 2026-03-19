package com.example.bankingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Loan {
    @Id
    private String loanId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @JoinColumn(name = "account_id")
    @JsonIgnore
    @ManyToOne
    private Account account;
}
