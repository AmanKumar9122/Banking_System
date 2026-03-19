package com.example.bankingsystem.entity.dto;

import lombok.Data;

@Data
public class LoanRequestDto {
    private Integer accountId;
    private double amount;
}