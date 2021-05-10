package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.HistoriqueStatutEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoriqueStatutRepository extends JpaRepository<HistoriqueStatutEntity, UUID> {
    List<HistoriqueStatutEntity> findHistoriqueStatutEntitiesByTransactionEntity(TransactionEntity transactionEntity);
    boolean existsByTransactionEntity(TransactionEntity transactionEntity);
    Optional<HistoriqueStatutEntity> findFirstByTransactionEntityOrderByDateCreationDesc(TransactionEntity transactionEntity);

}
