package com.adria.adriasign.dto.response;

import eu.europa.esig.dss.model.InMemoryDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncodedDocumentResponseDTO {
    private String documentName;
    private String encodedDocument;

    public static EncodedDocumentResponseDTO fromDssDocument(InMemoryDocument signedDocument) {
        return new EncodedDocumentResponseDTO(signedDocument.getBase64Encoded(), signedDocument.getName());
    }
}
