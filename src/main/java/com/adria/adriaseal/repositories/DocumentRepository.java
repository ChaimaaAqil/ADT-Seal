package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.DocumentEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.model.enums.StatutDocumentEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    List<DocumentEntity> findDocumentEntitiesByStatusAndTransactionEntity(StatutDocumentEnum statutDocument,TransactionEntity transactionEntity);
    Optional<DocumentEntity> findDocumentEntityByTransactionEntityAndId(TransactionEntity transactionEntity,UUID documentID);
    List<DocumentEntity> findDocumentEntitiesByTransactionEntity(TransactionEntity transactionEntity);
}
