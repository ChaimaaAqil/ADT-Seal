package com.adria.adriaseal.util;

import com.adria.adriaseal.exception.FileNotValidException;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.MimeType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class ApplicationUtils {
    private ApplicationUtils() {
    }
// cette methode permet de generer un nom au fichier pdf sauvgarder en local
public static String generateUniquePath(DSSDocument file) {
    return "/" + new Date().getTime() + "." + MimeType.getExtension(file.getMimeType());
}

    public static String extractMimeType(UUID id, String mimeType) {
        try {
            return StringUtils.cleanPath(id.toString() + "." + mimeType.split("/")[1]);
        } catch (IndexOutOfBoundsException ignored) {
            throw new FileNotValidException("Couldn't extract file extension from: " + mimeType);
        }
    }
}
