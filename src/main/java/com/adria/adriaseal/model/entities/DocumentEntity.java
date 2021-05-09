package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.StatutDocumentEnum;
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
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DocumentEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id;
    private String originDocName;

    private String mimeType;

    private long originDocSize;

    private Date dateUpload;
    @CreatedDate
    private Date dateInsert;

    private Date dateStartSign;

    private Date dateEndSign;

    @Enumerated(EnumType.STRING)
    private StatutDocumentEnum status;

    private String signedDocName;

    private String fullPathDocOrigin;

    private String fullPathDocSigned;

    private long signedDocSize;

    private String hashDocOrigin;

    private String hashDocSigned;

    private Date dateLastRequest;

    private boolean isVisible;

    private Integer originX;

    private Integer originY;

    private Integer height;

    private Integer width;

    private Integer pageSignature;

    @ManyToOne(targetEntity = TransactionEntity.class, fetch = FetchType.LAZY)
    private TransactionEntity transactionEntity;

    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity originDocRepertoireEntity;

    @ManyToOne(targetEntity = RepertoireEntity.class)
    private RepertoireEntity signedDocRepertoireEntity;

}
