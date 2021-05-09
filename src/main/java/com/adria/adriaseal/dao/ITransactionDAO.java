package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.TransactionEntity;

import java.util.UUID;

public interface ITransactionDAO {
    TransactionEntity init();

    TransactionEntity save(TransactionEntity transactionEntity);

    TransactionEntity findTransactionById(UUID transactionID);
}
