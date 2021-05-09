package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
@Entity
//@Table(name = "association_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppClienteKeystoreEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(updatable = false, nullable = false)
    private Date dateAssociation;
    private Boolean validated;
    @OneToMany( mappedBy = "appClienteKeystoreEntity" ,targetEntity = ApplicationClienteEntity.class, fetch = FetchType.LAZY)
    private Collection<ApplicationClienteEntity> applicationClientes;

    @OneToMany(mappedBy = "appClienteKeystoreEntity", targetEntity = KeyStoreEntity.class, fetch = FetchType.LAZY)
    private Collection<KeyStoreEntity> keyStoreEntities;
}
