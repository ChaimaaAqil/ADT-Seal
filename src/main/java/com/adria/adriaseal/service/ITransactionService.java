package com.adria.adriaseal.service;

import com.adria.adriaseal.dto.request.ClientAppInfosDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.dto.response.ClientAppInfosResponse;

import javax.transaction.Transaction;

public interface ITransactionService {
    ClientAppInfosResponse createTransaction(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParametersDTO, ClientAppInfosDTO clientAppInfosDTO);
}
