package com.adria.adriaseal.model.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.adria.adriaseal.model.enums.CodeBaseLineCertifEnum;

import javax.persistence.*;
import java.util.UUID;


@Entity
//@Table(name = "baseline_signatures")
@Data
@NoArgsConstructor @AllArgsConstructor
public class BaseLineCertifEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CodeBaseLineCertifEnum code;

    private String description;
}
