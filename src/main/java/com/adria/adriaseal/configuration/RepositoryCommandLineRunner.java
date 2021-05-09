package com.adria.adriaseal.configuration;

import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;
import com.adria.adriaseal.model.entities.KeyStoreEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;

@Transactional
@Configuration
public class RepositoryCommandLineRunner implements CommandLineRunner {
    // creer app cliente , client , certif
    @Override
    public void run(String... args) throws Exception {
        ApplicationClienteEntity applicationClienteEntity= new ApplicationClienteEntity();
        applicationClienteEntity.setCodeApp("CODE_APP_1");
        ClientEntity clientEntity =  new ClientEntity();
        clientEntity.setCodeClient("CODE_CLT_1");
        KeyStoreEntity keyStoreEntity = new KeyStoreEntity();
        keyStoreEntity.setCodeCertif("CODE_CRF_1");

    }
    // pour creer les dossiers PREUVE / ORIGN / SIGNED ... si ils ne sont pas crée !!
    //
}
