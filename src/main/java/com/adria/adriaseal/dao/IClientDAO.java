package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;

import java.util.UUID;

public interface IClientDAO {
    ClientEntity save(ClientEntity clientEntity, ApplicationClienteEntity clientApp);
    ClientEntity findById(UUID id);
    ClientEntity findByCodeClient(String codeClient);

}
