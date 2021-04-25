package com.adria.adriasign.util;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.MimeType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Service
public class ApplicationUtils {
    private ApplicationUtils() {
    }
// cette methode permet de generer un nom au fichier pdf sauvgarder en local
    public static String generateUniquePath(DSSDocument file) {
        return "/" + new Date().getTime() + "." + MimeType.getExtension(file.getMimeType());
    }

}
