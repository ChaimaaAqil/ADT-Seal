package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.HistoriqueStatutEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoriqueStatutRepository extends JpaRepository<HistoriqueStatutEntity, UUID> {
    List<HistoriqueStatutEntity> findHistoriqueStatutsByTransaction(TransactionEntity transactionEntity);
    boolean existsByTransaction(TransactionEntity transactionEntity);
    Optional<HistoriqueStatutEntity> findFirstByTransactionOrderByDateCreationDesc(TransactionEntity transactionEntity);
}
