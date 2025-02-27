package com.financeirosimplificado.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeirosimplificado.domain.user.User;
import com.financeirosimplificado.dto.TransactionDTO;
import com.financeirosimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public void createTransaction (TransactionDTO transaction) throws Exception{

        User payer = this.userService.findUserById(transaction.payerId());
        User payee = this.userService.findUserById(transaction.payeeId());
        
        this.userService.validateTransaction(payer, transaction.amount());

    }

}
