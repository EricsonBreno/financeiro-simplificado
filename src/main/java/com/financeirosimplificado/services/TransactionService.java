package com.financeirosimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financeirosimplificado.domain.transaction.Transaction;
import com.financeirosimplificado.domain.user.User;
import com.financeirosimplificado.dto.TransactionDTO;
import com.financeirosimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    RestTemplate restTemplate;

    public void createTransaction (TransactionDTO transaction) throws Exception{

        User payer = this.userService.findUserById(transaction.payerId());
        User payee = this.userService.findUserById(transaction.payeeId());
        
        this.userService.validateTransaction(payer, transaction.amount());

        boolean authorized = this.authorizeTransaction(payer, transaction.amount()); 

        if ( !authorized ) {
            throw new Exception("Transaction not authorized");
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setAmount(transaction.amount());
        newTransaction.setTimestamp(LocalDateTime.now());

    }

    public boolean authorizeTransaction(User payer, BigDecimal amount) {

        ResponseEntity<Map> authorizationResponse = this.restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {

            String message = (String) authorizationResponse.getBody().get("message").toString();

            return "Autorizado".equals(message);

        } else return false;

    }

}
