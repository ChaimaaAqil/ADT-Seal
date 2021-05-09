package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.CodeDigestAlgorithmEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
//@Table(name = "digest_algorithms")
@Data
@NoArgsConstructor @AllArgsConstructor
public class DigestAlgorithmEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CodeDigestAlgorithmEnum code;

    private String description;
}
