package com.adria.adriaseal.model.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
//@Table(name = "clientEntity")
@Data
@EqualsAndHashCode(exclude = {"transactions"})
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClientEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String gsm;
    private String firstName;
    private String lastName;
    private String fonction;
    private String organisation;
    private String ice;
    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Date dateInsert;
    @LastModifiedDate
    private Date dateUpdate;
    private String businessId;
    private String email;
    private String cin;
    private String codeClient;



    @OneToMany( mappedBy = "clientEntity" ,targetEntity = ApplicationClienteEntity.class, fetch = FetchType.LAZY)
    private Collection<ApplicationClienteEntity> applicationClientes;

    @OneToMany(mappedBy = "clients", targetEntity = KeyStoreEntity.class, fetch = FetchType.LAZY)
    private Collection<KeyStoreEntity> keyStoreEntities;

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }



}
