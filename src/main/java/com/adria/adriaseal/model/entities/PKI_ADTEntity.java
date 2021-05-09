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
//@Table(name = "autorites_certification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PKI_ADTEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private Date dateCreation;

    private String applicationName;

    private String description;

    @OneToMany(mappedBy = "pki_adtEntity", targetEntity = KeyStoreEntity.class, fetch = FetchType.LAZY)
    private Collection<KeyStoreEntity> keyStoreEntities;

}
