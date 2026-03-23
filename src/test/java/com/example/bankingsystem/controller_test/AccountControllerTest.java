package com.example.bankingsystem.controller_test;

import com.example.bankingsystem.controller.AccountController;
import com.example.bankingsystem.entity.Account;
import com.example.bankingsystem.entity.AccountType;
import com.example.bankingsystem.entity.dto.AccountDto;
import com.example.bankingsystem.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAccount() throws Exception {

        // request DTO
        AccountDto accountDto = new AccountDto(AccountType.SAVINGS, 1000.0);

        // mock response entity
        Account account = new Account();
        account.setId(1);
        account.setType(AccountType.SAVINGS);
        account.setBalance(1000.0);

        // mock service
        Mockito.when(accountService.createAccount(Mockito.eq(1), Mockito.any(AccountDto.class)))
                .thenReturn(account);

        // perform request + assertions
        mockMvc.perform(
                        post("/api/accounts/customer/1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(1000.0));
    }
}