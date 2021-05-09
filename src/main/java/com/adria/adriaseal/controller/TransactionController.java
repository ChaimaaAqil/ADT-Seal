package com.adria.adriaseal.controller;

import com.adria.adriaseal.dto.request.ClientAppInfosDTO;
import com.adria.adriaseal.dto.request.ClientDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.dto.response.ClientAppInfosResponse;
import com.adria.adriaseal.service.ITransactionService;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.api.endpoint.v1}/transactions")
@Validated

public class TransactionController {
    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientAppInfosResponse> TestAPI(@Valid @ModelAttribute DocumentDTO documentDTO,
                                                          @Valid @RequestPart DocumentSignatureParametersDTO[] documentSignatureParameters,
                                                          @Valid @RequestPart ClientAppInfosDTO clientAppInfosDTO
    ) {


        System.out.println("in transactionEntity certification");
        return ResponseEntity.ok()
                .body(transactionService.createTransaction(documentDTO, documentSignatureParameters, clientAppInfosDTO));
    }


}

