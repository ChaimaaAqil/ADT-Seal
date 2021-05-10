package com.adria.adriaseal.model.entities;

import com.adria.adriaseal.model.enums.StatutTransactionEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
public class TransactionEntity {
    // TODO Check Constraints
    @Id
    @GeneratedValue
    private UUID id; //
    private String idACTransaction;

    @Enumerated(EnumType.STRING)
    private StatutTransactionEnum statut; //
    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Date dateCreation; //

    private String sourceIpAddress; //

    private String userAgent; //

    private Date dateGenerationCachetage;

    @ManyToOne(targetEntity = FormatCertifEntity.class)
    private FormatCertifEntity formatCertifEntity;

    @ManyToOne(targetEntity = BaseLineCertifEntity.class)
    private BaseLineCertifEntity baselineCertifEntity;

    @ManyToOne(targetEntity = DigestAlgorithmEntity.class)
    private DigestAlgorithmEntity digestAlgorithmEntity;

    @OneToMany(mappedBy = "transactionEntity", targetEntity = HistoriqueStatutEntity.class, fetch = FetchType.LAZY)
    private Collection<HistoriqueStatutEntity> historiqueStatutEntities; //

    @ManyToOne(targetEntity = ApplicationClienteEntity.class)
    private ApplicationClienteEntity applicationCliente; //
    @ManyToOne(targetEntity = KeyStoreEntity.class)
    private KeyStoreEntity keyStoreEntity; //
    @ManyToOne(targetEntity = ImageCertifEntity.class)
    private ImageCertifEntity imageCertifEntity;

    @OneToOne(targetEntity = DossierPreuveEntity.class)
    @JoinColumn(unique = true)
    private DossierPreuveEntity dossierPreuveEntity;

    @OneToMany(mappedBy = "transactionEntity", targetEntity = DocumentEntity.class, fetch = FetchType.LAZY)
    private Collection<DocumentEntity> documentEntities; //


}
