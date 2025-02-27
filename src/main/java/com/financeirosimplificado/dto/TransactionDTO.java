package com.financeirosimplificado.dto;

import com.financeirosimplificado.domain.user.User;

public record TransactionDTO (Long payerId, Long payeeId, BigDecimal amount) {
    User payer = this.userService.findUserById(payerId);
    User payee = this.userService.findUserById(payeeId);
    this.userService.validateTransaction(payer, amount);

}
