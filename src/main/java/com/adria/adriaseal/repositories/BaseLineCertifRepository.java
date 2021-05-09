package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.BaseLineCertifEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseLineCertifRepository extends JpaRepository<BaseLineCertifEntity, UUID> {
}
