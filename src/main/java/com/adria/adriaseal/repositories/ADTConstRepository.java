package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.ADTConstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ADTConstRepository extends JpaRepository<ADTConstEntity, UUID> {
    boolean existsByCode(String code);
    Optional<ADTConstEntity> findADTConstByCode(String code);
}
