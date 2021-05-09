package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.FormatCertifEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FormatCertifRepository extends JpaRepository<FormatCertifEntity, UUID> {
}
