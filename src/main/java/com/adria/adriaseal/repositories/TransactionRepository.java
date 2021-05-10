package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    TransactionEntity findTransactionEntityByIdACTransaction(String idACTransaction);

}
