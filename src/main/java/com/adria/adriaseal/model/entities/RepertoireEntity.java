package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RepertoireEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;
    @CreatedDate
    private Date dateCreation;
    private String code;
    @Enumerated(EnumType.STRING)

    private TypeContenuRepertoireEnum contentType;

    private String fullPath;

    private String description;

}
