package com.financeirosimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.financeirosimplificado.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
