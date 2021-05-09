package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.DossierPreuveEntity;
import com.adria.adriaseal.model.entities.FichierPreuveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FichierPreuveRepository extends JpaRepository<FichierPreuveEntity, UUID> {
    List<FichierPreuveEntity> findFichierPreuvesByDossierPreuve(DossierPreuveEntity dossierPreuveEntity);
}
