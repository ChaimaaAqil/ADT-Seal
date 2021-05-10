package com.adria.adriaseal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientAppInfosDTO {
    @NotEmpty
    private String codeApp;
    @NotEmpty
    private String codeClient;
    @NotEmpty
    private String codeCertif;

}
