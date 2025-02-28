package com.financeirosimplificado.dto;

import java.math.BigDecimal;

import com.financeirosimplificado.domain.user.User;

public record TransactionDTO (Long payerId, Long payeeId, BigDecimal amount) {
    
}
