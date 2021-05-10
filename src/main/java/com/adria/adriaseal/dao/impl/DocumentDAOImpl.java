package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IDocumentDAO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.exception.FileNotValidException;
import com.adria.adriaseal.exception.GenericException;
import com.adria.adriaseal.mapper.DocumentMapper;
import com.adria.adriaseal.model.entities.DocumentEntity;
import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.repositories.DocumentRepository;
import com.adria.adriaseal.util.ApplicationUtils;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentDAOImpl implements IDocumentDAO {

    private final Tika tika;
    private final DocumentRepository documentRepository;
    @Resource(name = "PAdESSBaseLineEnvelopedSHA256Params")
    private PAdESSignatureParameters parameters;


    @Override
    public List<DocumentEntity> saveAllDocumentsOrign(TransactionEntity transactionEntity, MultipartFile[] documents, DocumentSignatureParametersDTO[] documentSignatureParameters, RepertoireEntity documentRepo, Date uploadDate) {
        return IntStream.range(0, documents.length)
                .mapToObj(index -> saveNonSignedDocument(transactionEntity, documents[index], documentSignatureParameters[index], documentRepo, uploadDate))
                .collect(Collectors.toList());
    }

    @Override
    public void saveSignedDocument(DocumentEntity document, InMemoryDocument signedDSSDocument, RepertoireEntity documentRepo) {

    }

    @Override
    public DocumentEntity saveNonSignedDocument(TransactionEntity transaction, MultipartFile multipartFile, DocumentSignatureParametersDTO params, RepertoireEntity nonSignedDocumentRepository, Date uploadDate) {
        UUID documentId = documentRepository.save(new DocumentEntity()).getId();
        try {
            if (multipartFile != null && multipartFile.getBytes().length != 0) {
                InMemoryDocument inMemoryDocument = new InMemoryDocument(multipartFile.getBytes());
                String mimeType = tika.detect(multipartFile.getBytes());
                String fileName = ApplicationUtils.extractMimeType(documentId, mimeType);
                File file = new File(nonSignedDocumentRepository.getFullPath(), fileName);
                if (!file.exists()) {
                    FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
                    DocumentEntity document = DocumentMapper.INSTANCE.multiPartFormDataToDocument(multipartFile, params)
                            .toBuilder()
                            .id(documentId)
                            .dateUpload(uploadDate)
                            .fullPathDocOrigin(fileName)
                            .mimeType(mimeType)
                            .hashDocOrigin(inMemoryDocument.getDigest(parameters.getDigestAlgorithm()))
                            .originDocRepertoireEntity(nonSignedDocumentRepository)
                            .transactionEntity(transaction)
                            .originDocSize(inMemoryDocument.getBytes().length)
                            .build();
                    document.setTransactionEntity(transaction);
                    return documentRepository.save(document);
                } else {
                    throw new FileNotValidException("File already exists: " + fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GenericException("Couldn't save the signature image!", e);
        }
        return null;
    }

}
