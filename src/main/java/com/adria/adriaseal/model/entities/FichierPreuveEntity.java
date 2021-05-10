package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;


@Entity
//@Table(name = "fichiers_preuve")
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class FichierPreuveEntity {
    @Id
    @GeneratedValue
    private UUID id;
    // TODO Check Constraints
    private String name;

    private String type;

    private String content;

    private String fullPathDoc;

    private String nameDoc;

    private String description;

    @ManyToOne(targetEntity = DossierPreuveEntity.class, fetch = FetchType.LAZY)
    private DossierPreuveEntity dossierPreuveEntity;

    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity repertoireEntity;

}
