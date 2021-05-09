package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.exception.GenericException;
import com.adria.adriaseal.service.ReturnTypeStrategy;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import com.adria.adriaseal.util.ApplicationUtils;
import com.adria.adriaseal.util.EnvUtil;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnURL implements ReturnTypeStrategy {

    private final EnvUtil envUtil;

    public ReturnURL(EnvUtil envUtil) {
        this.envUtil = envUtil;
    }

    @Override
    public Object returnFile(List<InMemoryDocument> inMemoryDocuments) {
        return inMemoryDocuments
                .stream()
                .map(inMemoryDocument -> {
                    String fileName = ApplicationUtils.generateUniquePath(inMemoryDocument);
                    try {
                        inMemoryDocument.save(envUtil.getUploadPath() + fileName);
                     //   System.out.println(envUtil.getServerUrl() + envUtil.getFilesEndpoint() + fileName);
                        return envUtil.getServerUrl() + envUtil.getFilesEndpoint() + fileName;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new GenericException("Couldn't save this file" + fileName, e);
                    }
                })
                .collect(Collectors.toList());

    }

    @Override
    public ReturnTypeStrategyName getStrategyName() {
        return ReturnTypeStrategyName.URL_STRATEGY;
    }
}
