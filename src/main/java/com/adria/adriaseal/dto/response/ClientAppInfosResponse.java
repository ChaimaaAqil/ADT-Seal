package com.adria.adriaseal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientAppInfosResponse {
    private String idTransaction;
    private String codeApp;
    private String codeClient;
    private String codeCertif;
}
