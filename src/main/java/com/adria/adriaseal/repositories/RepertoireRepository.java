package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepertoireRepository extends JpaRepository<RepertoireEntity, UUID> {

    Optional<RepertoireEntity> findRepertoireEntityByContentType(TypeContenuRepertoireEnum typeContenu);
    boolean existsByContentType(TypeContenuRepertoireEnum typeContenu);
}
