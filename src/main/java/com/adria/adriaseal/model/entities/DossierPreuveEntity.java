package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DossierPreuveEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private Date dateGeneration;

    private Date dateInitialisation;

    private String fullPathDoc;

    private String nameDoc;

    private Date dateLastRequest;

    @OneToOne(targetEntity = TransactionEntity.class)
    @JoinColumn(unique = true)
    private TransactionEntity transactionEntity;

    @OneToMany(mappedBy = "dossierPreuveEntity", targetEntity = FichierPreuveEntity.class, fetch = FetchType.LAZY)
    private Collection<FichierPreuveEntity> fichiersPreuve;

    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity repertoireEntity;
}
