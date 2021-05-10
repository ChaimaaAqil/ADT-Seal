package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.DossierPreuveEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DossierPreuveRepository extends JpaRepository<DossierPreuveEntity, UUID> {
    DossierPreuveEntity findDossierPreuveEntityByTransactionEntity(TransactionEntity transactionEntity);
}
