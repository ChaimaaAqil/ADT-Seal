package com.adria.adriasign.controller;

import com.adria.adriasign.dto.request.ClientDTO;
import com.adria.adriasign.dto.request.DocumentDTO;
import com.adria.adriasign.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriasign.service.ICertificationServiceV3;
import com.adria.adriasign.service.ReturnTypeStrategyName;
import com.adria.adriasign.service.impl.DocumentValidationService;
import eu.europa.esig.dss.pades.SignatureImageTextParameters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("${app.api.endpoint.v3}/documents/certification")
@Validated
public class FileCertificationAPIControllerV3 {
// this is develop/inv branch
    final ICertificationServiceV3 certificationService;
    final DocumentValidationService documentValidationService;

    public FileCertificationAPIControllerV3(ICertificationServiceV3 certificationService, DocumentValidationService documentValidationService) {
        this.certificationService = certificationService;
        this.documentValidationService = documentValidationService;
    }
    @PostMapping(value = "/test" )
    public ResponseEntity<Object> hello() {
        return ResponseEntity.ok()
                .body("hello");
    }
    @PostMapping(value = "/url", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invisibleSignDocumentAsURL(@Valid @ModelAttribute DocumentDTO documentDTO,
                                                             @Valid @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                             @Valid @RequestPart ClientDTO client
    ) {
        documentValidationService.verify(documentDTO, documentSignatureParameters);

        System.out.println("in transaction certification");
     //   System.out.println("Organisation :"+client.getOrganisation()+" ,BusinessId "+client.getBusinessId());
        return ResponseEntity.ok()
                .body(certificationService.certifMultipleDocuments(documentDTO, documentSignatureParameters,
                        client, ReturnTypeStrategyName.URL_STRATEGY));
    }
    @PostMapping(value = "/base64", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invisibleSignDocumentAsBase64(@ModelAttribute DocumentDTO documentDTO,
                                                                @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                                @RequestPart ClientDTO client) {

        documentValidationService.verify(documentDTO, documentSignatureParameters);
        return ResponseEntity.ok()
                .body(certificationService.certifMultipleDocuments(documentDTO, documentSignatureParameters, client, ReturnTypeStrategyName.BASE_64_STRATEGY));
    }

    @PostMapping(value = "/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/zip")
    public ResponseEntity<Object> invisibleSignDocumentAsZIP(@Valid @ModelAttribute DocumentDTO documentDTO,
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


}
