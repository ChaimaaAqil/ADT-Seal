package com.adria.adriaseal.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.adria.adriaseal.model.enums.CodeFormatCertifEnum;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class FormatCertifEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CodeFormatCertifEnum code;
    private String libelle;
}
