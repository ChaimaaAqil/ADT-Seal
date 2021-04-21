package com.adria.adriasign.configuration.validators;

import com.adria.adriasign.exception.MultipartFileException;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.MimeType;
import org.apache.tika.Tika;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContainsPDFsOnlyValidator implements ConstraintValidator<ContainsPDFsOnly, MultipartFile[]> {

    static Function<MultipartFile, InMemoryDocument> bytesToInMemoryDocument = document -> {
        try {
            if (document.getBytes().length == 0) throw new IOException("The file is empty or null");
            return new InMemoryDocument(document.getBytes(), document.getOriginalFilename());
        } catch (IOException e) {
            throw new MultipartFileException("Couldn't extract the file from the " + document.getName() +
                    " field, the object is empty!", e);
        }
    };
    private Tika tika;
    Predicate<InMemoryDocument> isNotPDF = document -> !tika.detect(document.getBytes()).equals(MimeType.PDF.getMimeTypeString());

    ContainsPDFsOnlyValidator(Tika tika) {
        this.tika = tika;
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if (value != null) {
            final List<String> nonPDFDocuments = Arrays.stream(value)
                    .map(bytesToInMemoryDocument)
                    .filter(isNotPDF).map(DSSDocument::getName).collect(Collectors.toList());

            if (!nonPDFDocuments.isEmpty()) {
                final String fileNames = String.join(", ", nonPDFDocuments);
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addExpressionVariable("pdfErrorMessage", "The following document(s) are not a PDF file: " + fileNames);
                return false;
            }
        }
        return true;
    }
}
