package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.ApplicationClienteEntity;

import java.util.UUID;

public interface IApplicationClienteDAO {
    ApplicationClienteEntity save(ApplicationClienteEntity applicationCliente);
    ApplicationClienteEntity findById(UUID id);
    ApplicationClienteEntity findByCodeApp(String codeApp);
}
