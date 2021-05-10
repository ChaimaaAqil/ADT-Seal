package com.adria.adriaseal.configuration;

import com.adria.adriaseal.exception.FileNotValidException;
import com.adria.adriaseal.model.entities.ApplicationClienteEntity;
import com.adria.adriaseal.model.entities.ClientEntity;
import com.adria.adriaseal.model.entities.KeyStoreEntity;
import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;
import com.adria.adriaseal.repositories.ApplicationClienteRepository;
import com.adria.adriaseal.repositories.ClientRepository;
import com.adria.adriaseal.repositories.KeyStoreRepository;
import com.adria.adriaseal.repositories.RepertoireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

@Transactional
@Configuration

public class RepositoryCommandLineRunner implements CommandLineRunner {
    // creer app cliente , client , certif
    RepertoireRepository repertoireRepository;
    @Autowired
    ApplicationClienteRepository applicationClienteRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    KeyStoreRepository keyStoreRepository;

    private Map<TypeContenuRepertoireEnum, String> repos;
    @Value("${app.save.DOCUMENT_ORIGIN}")
    private String documentOrigin;
    @Value("${app.save.DOCUMENT_SIGNE}")
    private String documentSigne;
    @Value("${app.save.DOSSIER_PREUVE}")
    private String dossierPreuve;
    @Value("${app.save.FICHIER_PREUVE}")
    private String fichierPreuve;
    @Value("${app.save.IMAGE_CERTIF}")
    private String imageCertif;
    @Value("${app.save.KEYSTORE}")
    private String keystore;
    @Autowired
    public RepositoryCommandLineRunner(RepertoireRepository repertoireRepository) {
        this.repertoireRepository = repertoireRepository;
        this.repos = new EnumMap<>(TypeContenuRepertoireEnum.class);
    }
    public RepositoryCommandLineRunner(RepertoireRepository repertoireRepository, Map<TypeContenuRepertoireEnum, String> repos) {
        this(repertoireRepository);
        this.repos = repos;
    }
    @Override
    public void run(String... args) throws Exception {
       applicationClienteRepository.deleteAll();
        clientRepository.deleteAll();
        keyStoreRepository.deleteAll();

        ApplicationClienteEntity applicationClienteEntity = new ApplicationClienteEntity();
        applicationClienteEntity.setCodeApp("CODE_APP_1");
        applicationClienteEntity.setDateInsert(new Date());
        applicationClienteEntity.setWsInvisibleIn(false);
        applicationClienteEntity.setWsVisibleIn(false);
        applicationClienteEntity.setOriginX((float)100);
        applicationClienteEntity.setOriginY((float) 700);
        applicationClienteEntity.setPageCertif(1);
        applicationClienteRepository.save(applicationClienteEntity);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCodeClient("CODE_CLT_1");
        clientEntity.setDateInsert(new Date());
        clientRepository.save(clientEntity);

        KeyStoreEntity keyStoreEntity = new KeyStoreEntity();
        keyStoreEntity.setCodeCertif("CODE_CRF_1");
        keyStoreEntity.setDateGeneration(new Date());
        keyStoreEntity.setClients(clientEntity);
        keyStoreRepository.save(keyStoreEntity);


//        ApplicationClienteEntity applicationClienteEntity = ApplicationClienteEntity.builder()
//                .codeApp("CODE_APP_1")
//                .dateInsert(new Date())
//                .build();
//        ClientEntity clientEntity = ClientEntity.builder()
//                .codeClient("CODE_CLT_1")
//                .dateInsert(new Date())
//                .build();
//        KeyStoreEntity keyStoreEntity = KeyStoreEntity.builder()
//                .codeCertif("CODE_CRF_1")
//                .dateGeneration(new Date())
//                .build();
        // create folders
        if (repos.isEmpty()) { repos.put(TypeContenuRepertoireEnum.DOCUMENT_ORIGINE, documentOrigin);repos.put(TypeContenuRepertoireEnum.DOCUMENT_SIGNE, documentSigne);repos.put(TypeContenuRepertoireEnum.DOSSIER_PREUVE, dossierPreuve);repos.put(TypeContenuRepertoireEnum.FICHIER_PREUVE, fichierPreuve);repos.put(TypeContenuRepertoireEnum.IMAGE_CERTIF, imageCertif);repos.put(TypeContenuRepertoireEnum.KEYSTORE, keystore); }
        repos.entrySet().forEach(this::createAndSaveRepository);

    }

    private void createAndSaveRepository(Map.Entry<TypeContenuRepertoireEnum, String> entry) {
        File directory = new File(entry.getValue());
        RepertoireEntity repertoire = RepertoireEntity
                .builder()
                .contentType(entry.getKey())
                .fullPath(entry.getValue())
                .build();
        if (!directory.exists()) {
            if (directory.mkdirs())
                repertoireRepository.save(repertoire);
            else
                throw new FileNotValidException("Couldn't create the folder: " + entry.getValue() +
                        ", might already exists or there was a IOException");
        } else {
            // If it doesn't exists on the database add it
            if (!repertoireRepository.existsByContentType(entry.getKey()))
                repertoireRepository.save(repertoire);
        }
    }
}
