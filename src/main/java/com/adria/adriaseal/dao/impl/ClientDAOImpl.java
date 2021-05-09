package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IClientDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;
import com.adria.adriaseal.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional

public class ClientDAOImpl implements IClientDAO {
    private final ClientRepository clientRepository;

    public ClientDAOImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity, ApplicationClienteEntity clientApps) {
     /*   clientEntity.setApplicationClientes((Collection<ApplicationClienteEntity>) clientApps);
        return clientRepository.save(clientEntity);*/
        return null;
    }

    @Override
    public ClientEntity findById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Client  with the id not %s found", id)));
    }
}