package com.adria.adriasign.configuration.validators;

import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.MimeType;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

public class IsAnImageValidator implements ConstraintValidator<IsAnImage, MultipartFile[]> {
    private Tika tika;
    Predicate<InMemoryDocument> isImage = document ->
            tika.detect(document.getBytes()).equals(MimeType.PNG.getMimeTypeString()) ||
                    tika.detect(document.getBytes()).equals(MimeType.JPEG.getMimeTypeString()) ||
                    tika.detect(document.getBytes()).equals(MimeType.SVG.getMimeTypeString());

    IsAnImageValidator(Tika tika) {
        this.tika = tika;
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if (value != null) {
            try {
                if (value.length > 1) throw new IndexOutOfBoundsException();
                if (value[0].getBytes().length == 0) {
                    return true;
                }
                return Arrays.stream(value).map(ContainsPDFsOnlyValidator.bytesToInMemoryDocument)
                        .allMatch(isImage);

            } catch (IndexOutOfBoundsException | IOException exception) {
                return true;
            }
        }
        return true;
    }
}
