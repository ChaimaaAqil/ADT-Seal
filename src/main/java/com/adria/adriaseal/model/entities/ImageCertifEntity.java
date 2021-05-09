package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ImageCertifEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @CreatedDate
    private Date dateInsert;
    private String fullPathImage;
    private String imageName;

    private long originSize;

    private Date dateUpload;
    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity repertoireEntity;
    @OneToMany(mappedBy = "imageCertifEntity", targetEntity = TransactionEntity.class, fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactionEntities;
}
