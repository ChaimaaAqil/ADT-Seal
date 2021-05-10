package com.adria.adriaseal.dao;

import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.model.entities.DocumentEntity;
import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.model.enums.StatutDocumentEnum;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IDocumentDAO {


    List<DocumentEntity> saveAllDocumentsOrign(TransactionEntity transactionEntity, MultipartFile[] documents,
                                                DocumentSignatureParametersDTO[] documentSignatureParameters, RepertoireEntity documentRepo, Date uploadDate);

    void saveSignedDocument(DocumentEntity document, InMemoryDocument signedDSSDocument, RepertoireEntity documentRepo);
    DocumentEntity saveNonSignedDocument(TransactionEntity transaction, MultipartFile multipartFile, DocumentSignatureParametersDTO params,
                                   RepertoireEntity documentRepo, Date uploadDate);
}
