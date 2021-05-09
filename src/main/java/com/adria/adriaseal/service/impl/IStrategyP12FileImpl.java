package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.service.IStrategyP12File;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStore;

@Service
public class IStrategyP12FileImpl implements IStrategyP12File {
    // cette classe permet de faire l'appel au p12 pour certifier les documentEntities
  @Value("classpath:bsabir_endentity.p12")

//    @Value("classpath:clientEntity-identity.p12")
    Resource p12File;

    @Override
    public Pkcs12SignatureToken getPKCS12SignatureToken() {
        try {
            return new Pkcs12SignatureToken(p12File.getFile(),
                      new KeyStore.PasswordProtection("123456".toCharArray()));
//                    new KeyStoreEntity.PasswordProtection("test258".toCharArray()));
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }
}
