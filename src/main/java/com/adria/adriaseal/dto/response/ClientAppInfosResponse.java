package com.adria.adriaseal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientAppInfosResponse {
    private UUID idTransaction;
    private String codeApp;
    private String codeClient;
    private String codeCertif;
    private byte[] signedDocuments;
}
