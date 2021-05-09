package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.PKI_ADTEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PKI_ADTRepository extends JpaRepository<PKI_ADTEntity, UUID> {
}
