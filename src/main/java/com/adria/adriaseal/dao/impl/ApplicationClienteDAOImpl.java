package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IApplicationClienteDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.repositories.ApplicationClienteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class ApplicationClienteDAOImpl implements IApplicationClienteDAO {

    private final ApplicationClienteRepository applicationClienteRepository;

    public ApplicationClienteDAOImpl(ApplicationClienteRepository applicationClienteRepository) {
        this.applicationClienteRepository = applicationClienteRepository;
    }


    @Override
    public ApplicationClienteEntity save(ApplicationClienteEntity applicationCliente) {
        return applicationClienteRepository.save(applicationCliente);
    }

    @Override
    public ApplicationClienteEntity findById(UUID id) {
        return applicationClienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Client app with the id not %s found", id)));
    }

    @Override
    public ApplicationClienteEntity findByCodeApp(String codeApp) {
        return applicationClienteRepository.findByCodeApp(codeApp);

    }
}

