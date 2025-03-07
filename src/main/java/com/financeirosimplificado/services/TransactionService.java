package com.financeirosimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

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

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction (TransactionDTO transaction) throws Exception{

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

        payer.setBalance(payer.getBalance().subtract(transaction.amount()));
        payee.setBalance(payee.getBalance().add(transaction.amount()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        this.notificationService.sendNotification(payer, "Transaction completed successfully");
        this.notificationService.sendNotification(payee, "Transaction received successfully");

        return newTransaction;

    }

    public boolean authorizeTransaction(User payer, BigDecimal amount) {

        ResponseEntity<Map> authorizationResponse = this.restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {

            Map<String, Object> responseBody = authorizationResponse.getBody();

            if (responseBody == null) {
                return false;
            }

            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            if (data == null) {
                return false;
            }

            Boolean authorized = (Boolean) data.get("authorization");

            return authorized;

        } else return false;

    }

}
