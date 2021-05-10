package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.ClientAppResponseModeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
public class ApplicationClienteEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String codeApp;


    private String libelle;
    private String description;
    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Date dateInsert;
    @LastModifiedDate
    private Date dateUpdate;
    @Enumerated(EnumType.STRING)
    private ClientAppResponseModeEnum modeResponse;
    private String domaine;
    @NotNull
    private Boolean wsInvisibleIn;
    @NotNull
    private Boolean wsVisibleIn;
    @Range(min = 0, max = 30000)
    private Float originX;
    @Range(min = 0, max = 30000)
    private Float originY;
    @Positive
    private Integer pageCertif;

    private String wsOutput;

    @ManyToOne(targetEntity = ClientEntity.class,fetch = FetchType.LAZY)
    private ClientEntity clientEntity;
    @ManyToOne(targetEntity = AppClienteKeystoreEntity.class,fetch = FetchType.LAZY)
    private AppClienteKeystoreEntity appClienteKeystoreEntity;
    @OneToMany(mappedBy = "applicationCliente", targetEntity = TransactionEntity.class, fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactionEntities;




}
