package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.service.ReturnTypeStrategy;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReturnBase64 implements ReturnTypeStrategy {

    @Override
    public Object returnFile(List<InMemoryDocument> signedDocument) {

        return signedDocument.stream().map(InMemoryDocument::getBase64Encoded).collect(Collectors.toList());
    }

    @Override
    public ReturnTypeStrategyName getStrategyName() {
        return ReturnTypeStrategyName.BASE_64_STRATEGY;
    }
}

