package com.adria.adriaseal.service;

import eu.europa.esig.dss.model.InMemoryDocument;

import java.util.List;

public interface ReturnTypeStrategy {

    // We can return a ZIP, List of URL or a List of Base64 representation of the files
    Object returnFile(List<InMemoryDocument> inMemoryDocument);

    ReturnTypeStrategyName getStrategyName();

}
