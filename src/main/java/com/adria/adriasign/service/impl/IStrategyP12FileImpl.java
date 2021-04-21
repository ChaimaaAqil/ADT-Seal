package com.adria.adriasign.service.impl;

import com.adria.adriasign.service.IStrategyP12File;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStore;

@Service
public class IStrategyP12FileImpl implements IStrategyP12File {
    @Value("classpath:youssef_endentity.p12")
    Resource p12File;

    @Override
    public Pkcs12SignatureToken getPKCS12SignatureToken() {
        try {
            return new Pkcs12SignatureToken(p12File.getFile(),
                    new KeyStore.PasswordProtection("test258".toCharArray()));
        } catch (IOException e) {

            //TODO handle : failed to decrypt safe contents entry throws exception java.security.UnrecoverableKeyException
            e.printStackTrace();
            return null;
        }
    }
}
