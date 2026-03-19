package com.example.bankingsystem.entity.dto;

import com.example.bankingsystem.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountDto {
    private AccountType type;
    private double initialDeposit;
}
