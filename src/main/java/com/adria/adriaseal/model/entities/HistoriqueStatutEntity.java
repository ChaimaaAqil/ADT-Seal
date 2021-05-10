package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.StatutTransactionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class HistoriqueStatutEntity {
    @Id
    @GeneratedValue
    private UUID id;

    // TODO Check Constraints
    @Enumerated(EnumType.STRING)
    private StatutTransactionEnum status;
    @Column(updatable = false, nullable = false)
    @CreatedDate
    protected Date dateCreation;
    @LastModifiedDate
    private Date dateUpdate;

    private Duration duration;

    @ManyToOne(targetEntity = TransactionEntity.class)
    private TransactionEntity transactionEntity;


}
