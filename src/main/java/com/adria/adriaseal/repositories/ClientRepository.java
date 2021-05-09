package com.adria.adriaseal.repositories;

import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository  extends JpaRepository<ClientEntity, UUID> {
    List<ClientEntity> findSignatairesByApplicationCliente(ApplicationClienteEntity applicationCliente);
}
