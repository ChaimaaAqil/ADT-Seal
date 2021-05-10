package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IKeyStoreDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.KeyStoreEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.repositories.KeyStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class KeyStoreDAOImpl implements IKeyStoreDAO {
    private final KeyStoreRepository keyStoreRepository;
    @Override
    public KeyStoreEntity save(TransactionEntity transactionEntity) {
        return null;
    }

    @Override
    public KeyStoreEntity setKeyStoreCodeCertif(KeyStoreEntity keyStoreEntity, String password) {
        return null;
    }

    @Override
    public void deleteKeyStore(KeyStoreEntity keyStoreEntity) {

    }

    @Override
    public KeyStoreEntity findById(UUID id) {
        return keyStoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Keystore  with the id not %s found", id)));
    }

    @Override
    public KeyStoreEntity findByCodeCertif(String codeCertif) {
        return keyStoreRepository.findByCodeCertif(codeCertif);
    }
}
