package com.adria.adriaseal.controller;

import com.adria.adriaseal.dto.request.ClientDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.service.ICertificationServiceV1;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import com.adria.adriaseal.service.impl.DocumentValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.api.endpoint.v1}/documents/certification")
@Validated

public class FileCertificationAPIControllerV1 {
   /* final ICertificationServiceV1 certificationService;
    final DocumentValidationService documentValidationService;
    // certifDocumentsAsURL
    // certifDocumentsAsBase64
    // certifDocumentsAsZIP


    public FileCertificationAPIControllerV1(ICertificationServiceV1 certificationService, DocumentValidationService documentValidationService) {
        this.certificationService = certificationService;
        this.documentValidationService = documentValidationService;
    }

    @PostMapping(value = "/url", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> certifDocumentsAsURL(@Valid @ModelAttribute DocumentDTO documentDTO,
                                                       @Valid @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                       @Valid @RequestPart ClientDTO client
    ) {
        documentValidationService.verify(documentDTO, documentSignatureParameters);

        System.out.println("in transactionEntity certification");
        return ResponseEntity.ok()
                .body(certificationService.certifMultipleDocuments(documentDTO, documentSignatureParameters,
                        client, ReturnTypeStrategyName.URL_STRATEGY));
    }

    @PostMapping(value = "/base64", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> certifDocumentsAsBase64(@ModelAttribute DocumentDTO documentDTO,
                                                          @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                          @RequestPart ClientDTO client) {

        documentValidationService.verify(documentDTO, documentSignatureParameters);
        return ResponseEntity.ok()
                .body(certificationService.certifMultipleDocuments(documentDTO, documentSignatureParameters, client, ReturnTypeStrategyName.BASE_64_STRATEGY));
    }

    @PostMapping(value = "/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/zip")
    public ResponseEntity<Object> certifDocumentsAsZIP(@Valid @ModelAttribute DocumentDTO documentDTO,
                                                       @Valid @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                       @Valid @RequestPart ClientDTO client) {

        documentValidationService.verify(documentDTO, documentSignatureParameters);

        HttpHeaders headers = new HttpHeaders();
        String outputFilename = "output.zip";
        headers.add("Content-Disposition", "attachment; filename=" + outputFilename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return ResponseEntity.ok()
                .headers(headers)
                .body(certificationService.certifMultipleDocuments(documentDTO, documentSignatureParameters, client, ReturnTypeStrategyName.ZIP_STRATEGY));
    }

*/
}
