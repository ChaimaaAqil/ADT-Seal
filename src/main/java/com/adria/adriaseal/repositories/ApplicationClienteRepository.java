package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationClienteRepository extends JpaRepository<ApplicationClienteEntity, UUID> {
}
