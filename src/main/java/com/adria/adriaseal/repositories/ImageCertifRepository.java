package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.ImageCertifEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageCertifRepository extends JpaRepository<ImageCertifEntity, UUID> {
}
