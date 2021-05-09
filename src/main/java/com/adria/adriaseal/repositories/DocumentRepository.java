package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.DocumentEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.model.enums.StatutDocumentEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    List<DocumentEntity> findDocumentsByTransaction(TransactionEntity transactionEntity);
    List<DocumentEntity> findDocumentsByStatusAndTransaction(StatutDocumentEnum statutDocument, TransactionEntity transactionEntity);
    Optional<DocumentEntity> findDocumentByTransactionAndId(TransactionEntity transactionEntity, UUID documentID);
}
