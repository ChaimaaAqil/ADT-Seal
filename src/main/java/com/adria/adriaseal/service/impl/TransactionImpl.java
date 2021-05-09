package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.dao.IApplicationClienteDAO;
import com.adria.adriaseal.dao.IClientDAO;
import com.adria.adriaseal.dao.IKeyStoreDAO;
import com.adria.adriaseal.dao.ITransactionDAO;
import com.adria.adriaseal.dto.request.ClientAppInfosDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.dto.response.ClientAppInfosResponse;
import com.adria.adriaseal.model.entities.*;
import com.adria.adriaseal.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements ITransactionService {
    private final ITransactionDAO transactionDAO;
    private final IApplicationClienteDAO applicationClienteDAO;
    private final IClientDAO clientDAO;
    private final IKeyStoreDAO keyStoreDAO;
    @Override
    public ClientAppInfosResponse createTransaction(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParametersDTO, ClientAppInfosDTO clientAppInfosDTO) {
        Date uploadDate = new Date();
        TransactionEntity transactionEntity = transactionDAO.init();
        // permet de recuperer l'aplication cliente a partir de code_app
        ApplicationClienteEntity clientApp = applicationClienteDAO.findById(UUID.fromString(clientAppInfosDTO.getCodeApp()));
        ClientEntity clientEntity = clientDAO.findById(UUID.fromString(clientAppInfosDTO.getCodeClient()));
        KeyStoreEntity keyStoreEntity = keyStoreDAO.findById(UUID.fromString(clientAppInfosDTO.getCodeClient()));
        // save documents Origne
        transactionEntity.setApplicationCliente(clientApp);
        transactionEntity.setKeyStoreEntity(keyStoreEntity);
       // Save transaction
        transactionDAO.save(transactionEntity);
        // Signature des documents

        //Client App infos response
        ClientAppInfosResponse clientAppInfosResponse = new ClientAppInfosResponse();
        clientAppInfosResponse.setIdTransaction(transactionEntity.getId().toString());
        clientAppInfosResponse.setCodeApp(clientApp.getCodeApp());
        clientAppInfosResponse.setCodeClient(clientEntity.getCodeClient());
        clientAppInfosResponse.setCodeCertif(keyStoreEntity.getCodeCertif());

        return clientAppInfosResponse;
    }
}
