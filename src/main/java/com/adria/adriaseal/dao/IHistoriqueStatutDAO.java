package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.TransactionEntity;

public interface IHistoriqueStatutDAO {
    void save(TransactionEntity transactionEntity);
    void logErrorOccurred(TransactionEntity transactionEntity);
}
