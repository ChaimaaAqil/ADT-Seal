package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.dao.*;
import com.adria.adriaseal.dto.request.ClientAppInfosDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.dto.response.ClientAppInfosResponse;
import com.adria.adriaseal.model.entities.*;
import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;
import com.adria.adriaseal.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements ITransactionService {
    private final ITransactionDAO transactionDAO;
    private final IApplicationClienteDAO applicationClienteDAO;
    private final IClientDAO clientDAO;
    private final IKeyStoreDAO keyStoreDAO;
    private final IDocumentDAO documentDAO;
    private  final IRepertoireDAO repertoireDAO;

    @Override
    public ClientAppInfosResponse createTransaction(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParametersDTO, ClientAppInfosDTO clientAppInfosDTO) {
        Date uploadDate = new Date();
        TransactionEntity transactionEntity = transactionDAO.init();
        System.out.println("***************Transaction is initied************");
        // permet de recuperer l'aplication cliente a partir de code_app
        ApplicationClienteEntity clientApp = applicationClienteDAO.findByCodeApp(clientAppInfosDTO.getCodeApp());

        ClientEntity clientEntity = clientDAO.findByCodeClient(clientAppInfosDTO.getCodeClient());
        KeyStoreEntity keyStoreEntity = keyStoreDAO.findByCodeCertif(clientAppInfosDTO.getCodeCertif());

        System.out.println(clientApp.getCodeApp());
        System.out.println(clientEntity.getCodeClient());
        System.out.println(keyStoreEntity.getCodeCertif());
        // find Repertoire Origin
        RepertoireEntity originalDocumentRepository = repertoireDAO.findRepertoireByContentType(TypeContenuRepertoireEnum.DOCUMENT_ORIGINE);

        // save documents Origne
        List<DocumentEntity> documentList = documentDAO.saveAllDocumentsOrign(transactionEntity, documentDTO.getDocuments(),
                documentSignatureParametersDTO, originalDocumentRepository, uploadDate);
        // seal documents
        // save signed documents

        transactionEntity.setApplicationCliente(clientApp);
        transactionEntity.setKeyStoreEntity(keyStoreEntity);
        // Save transaction
        transactionDAO.save(transactionEntity);

     //   System.out.println("***************Transaction is Saved ************");
//System.out.println(transactionEntity);
        // Signature des documents

        return new ClientAppInfosResponse(transactionEntity.getId(),clientApp.getCodeApp(),clientEntity.getCodeClient(),keyStoreEntity.getCodeCertif(),null);
    }


}
