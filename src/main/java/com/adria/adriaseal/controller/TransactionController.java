package com.adria.adriaseal.controller;

import com.adria.adriaseal.dto.request.*;
import com.adria.adriaseal.dto.response.ClientAppInfosResponse;
import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;
import com.adria.adriaseal.model.entities.KeyStoreEntity;
import com.adria.adriaseal.repositories.ApplicationClienteRepository;
import com.adria.adriaseal.repositories.ClientRepository;
import com.adria.adriaseal.repositories.KeyStoreRepository;
import com.adria.adriaseal.service.ITransactionService;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import com.adria.adriaseal.service.impl.DocumentValidationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("${app.api.endpoint.v1}/transactions")
@Validated
@RequiredArgsConstructor

public class TransactionController {
    private final ITransactionService transactionService;
    private final DocumentValidationService documentValidationService;



    @PostMapping(value = "/test")
    public ResponseEntity<Object> testAPI()
    {


        System.out.println("Test API successfully ");
        return ResponseEntity.ok()
                .body("Test API successfully");
    }


    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientAppInfosResponse> TestAPI(@Valid @ModelAttribute DocumentDTO documentDTO,
                                                          @Valid @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                           @Valid @RequestPart ClientAppInfosDTO clientAppInfosDTO
    ) {
        documentValidationService.verify(documentDTO, documentSignatureParameters);
        System.out.println("in transactionEntity certification");
        return ResponseEntity.ok()
                .body(transactionService.createTransaction(documentDTO, documentSignatureParameters, clientAppInfosDTO));
    }

}

