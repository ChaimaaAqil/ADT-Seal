package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.StatutOperateurRAEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
//@Table(name = "operateurRAEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OperateurRAEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private String firstName;
    private String lastName;
    private Date dateLastLogin;
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private StatutOperateurRAEnum statut;

    @OneToMany(mappedBy = "operateurRAEntity", targetEntity = KeyStoreEntity.class, fetch = FetchType.LAZY)
    private Collection<KeyStoreEntity> keyStoreEntities;

}
