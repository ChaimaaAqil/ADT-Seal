package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
//@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ADTConstEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String value;
}
