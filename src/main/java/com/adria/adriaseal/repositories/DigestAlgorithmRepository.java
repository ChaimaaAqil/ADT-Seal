package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.DigestAlgorithmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DigestAlgorithmRepository extends JpaRepository<DigestAlgorithmEntity, UUID> {
}
