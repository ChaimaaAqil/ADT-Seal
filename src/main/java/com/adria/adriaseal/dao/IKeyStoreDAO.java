package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.ClientEntity;
import com.adria.adriaseal.model.entities.KeyStoreEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;

import java.util.UUID;

public interface IKeyStoreDAO {
    KeyStoreEntity save(TransactionEntity transactionEntity);
    KeyStoreEntity setKeyStoreCodeCertif(KeyStoreEntity keyStoreEntity, String password);
    void deleteKeyStore(KeyStoreEntity keyStoreEntity);
    KeyStoreEntity findById(UUID id);

}
