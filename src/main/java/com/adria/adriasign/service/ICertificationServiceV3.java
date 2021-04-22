package com.adria.adriasign.service;

import com.adria.adriasign.dto.request.ClientDTO;
import com.adria.adriasign.dto.request.DocumentDTO;
import com.adria.adriasign.dto.request.DocumentSignatureParametersDTO;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.web.multipart.MultipartFile;

public interface ICertificationServiceV3 {
    Object certifMultipleDocuments(DocumentDTO signatureParameters, DocumentSignatureParametersDTO[] documentSignatureParameters,
                                 ClientDTO client, ReturnTypeStrategyName returnTypeStrategy);

    InMemoryDocument certifInvisiblyADocument(MultipartFile documentToBeCertified);

    InMemoryDocument certifVisiblyADocument(MultipartFile documentToBeCertified, MultipartFile signatureImage, DocumentSignatureParametersDTO documentSignatureParametersDTO, ClientDTO clientDTO);

}
