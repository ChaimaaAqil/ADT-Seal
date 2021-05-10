package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.KeyStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeyStoreRepository extends JpaRepository<KeyStoreEntity, UUID> {
    KeyStoreEntity findByCodeCertif(String codeCertif);
}

