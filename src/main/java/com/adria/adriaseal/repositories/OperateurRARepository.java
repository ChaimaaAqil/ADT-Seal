package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.OperateurRAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperateurRARepository extends JpaRepository<OperateurRAEntity, UUID> {
}

