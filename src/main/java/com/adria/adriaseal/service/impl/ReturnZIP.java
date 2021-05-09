package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.exception.GenericException;
import com.adria.adriaseal.service.ReturnTypeStrategy;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import eu.europa.esig.dss.model.InMemoryDocument;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ReturnZIP implements ReturnTypeStrategy {
    @Override
    public Object returnFile(List<InMemoryDocument> inMemoryDocuments) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            inMemoryDocuments.forEach(file -> {
                ZipEntry entry = new ZipEntry(file.getName());
                try {
                    zipOutputStream.putNextEntry(entry);
                    zipOutputStream.write(file.getBytes());
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new GenericException("There were some problems constructing the ZIP file!", ioe);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public ReturnTypeStrategyName getStrategyName() {
        return ReturnTypeStrategyName.ZIP_STRATEGY;
    }
}
