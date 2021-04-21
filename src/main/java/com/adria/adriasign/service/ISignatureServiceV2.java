package com.adria.adriasign.service;

import com.adria.adriasign.dto.request.ClientDTO;
import com.adria.adriasign.dto.request.DocumentDTO;
import com.adria.adriasign.dto.request.DocumentSignatureParametersDTO;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.web.multipart.MultipartFile;

public interface ISignatureServiceV2 {

    Object signMultipleDocuments(DocumentDTO signatureParameters, DocumentSignatureParametersDTO[] documentSignatureParameters,
                                 ClientDTO client, ReturnTypeStrategyName returnTypeStrategy);

    InMemoryDocument signInvisiblyADocument(MultipartFile documentToBeSign);

    InMemoryDocument signVisiblyADocument(MultipartFile documentToBeSign, MultipartFile signatureImage, DocumentSignatureParametersDTO documentSignatureParametersDTO, ClientDTO clientDTO);

}
