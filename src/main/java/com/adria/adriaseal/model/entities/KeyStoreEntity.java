package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.StatutKeyStoreEnum;
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
public class KeyStoreEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;
    private String codeCertif;



    private Date dateInvocation;
    private Date dateGeneration;
    private String fullPathCertificate;
    private String certificateName;
    private Date dateExperitation;
    @Enumerated(EnumType.STRING)
    private StatutKeyStoreEnum status;
    private String description;

    @ManyToOne(targetEntity = OperateurRAEntity.class, fetch = FetchType.LAZY)
    private OperateurRAEntity operateurRAEntity;

    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity repertoireEntity;
    @ManyToOne(optional = false)
    private ClientEntity clients;
    @ManyToOne(targetEntity = AppClienteKeystoreEntity.class,fetch = FetchType.LAZY)
    private AppClienteKeystoreEntity appClienteKeystoreEntity;
    @ManyToOne(targetEntity = PKI_ADTEntity.class,fetch = FetchType.LAZY)
    private PKI_ADTEntity pki_adtEntity;
    @OneToMany(mappedBy = "keyStoreEntity", targetEntity = TransactionEntity.class, fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactionEntities;


}
