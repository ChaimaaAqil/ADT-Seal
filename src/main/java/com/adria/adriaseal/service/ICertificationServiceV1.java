package com.adria.adriaseal.service;

import com.adria.adriaseal.dto.request.ClientDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.web.multipart.MultipartFile;

public interface ICertificationServiceV1 {
    Object certifMultipleDocuments(DocumentDTO signatureParameters, DocumentSignatureParametersDTO[] documentSignatureParameters,
                                 ClientDTO client, ReturnTypeStrategyName returnTypeStrategy);

    InMemoryDocument certifInvisiblyADocument(MultipartFile documentToBeCertified);

    InMemoryDocument certifVisiblyADocument(MultipartFile documentToBeCertified, MultipartFile signatureImage, DocumentSignatureParametersDTO documentSignatureParametersDTO, ClientDTO clientDTO);

}
