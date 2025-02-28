package com.financeirosimplificado.dto;

import java.math.BigDecimal;
public record TransactionDTO (Long payerId, Long payeeId, BigDecimal amount) {
    
}
