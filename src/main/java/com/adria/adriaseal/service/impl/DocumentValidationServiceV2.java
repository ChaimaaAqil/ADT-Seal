package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class DocumentValidationServiceV2 {
    /*
      * The number of documents should be equal to the number of documentsParameters(isVisible, X and Y positions).
      * we can consider a documentsParameter as valid if it has the following criterion :
          - isVisible is set to true and x and y are not null and valid.
          - isVisible is set to false and x, y, width, and height are not specified.
      * The number of images should be equal to the number of valid visible signatures.
      * the files to be signed should only contains pdf files.
      * the images to be used should only contain well known images format like PNG, JPEG and SVG.
  */
    public void verify(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParameters) {
        List<FieldError> fieldErrorList = new ArrayList<>();
        String lengthField = "length";
        String objectName = "documentSignatureParameters";

        // The number of document should be equal to the number of documentsParameters(isVisible, X and Y positions).
        if (documentDTO.getDocuments().length != documentSignatureParameters.length) {
            fieldErrorList.add(new FieldError(objectName, lengthField, "documents", true, null, null,
                    String.format("The number of documents : %d, should be equal to the number of documentsParameters (isVisible, X and Y positions): %d!",
                            documentDTO.getDocuments().length, documentSignatureParameters.length)));

        }
        boolean hasErrors = getDocumentSignatureParametersErrors(documentSignatureParameters, fieldErrorList);
        if (hasErrors) {
            validateDocumentsParams(documentSignatureParameters, fieldErrorList);
        }
        if (!fieldErrorList.isEmpty()) {
            throw new ValidationException(fieldErrorList);
        }
    }

    private boolean getDocumentSignatureParametersErrors(DocumentSignatureParametersDTO[] documentSignatureParameters, List<FieldError> fieldErrorList) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<FieldError> documentSignatureParametersErrors = Arrays.stream(documentSignatureParameters)
                .map(validator::validate)
                .flatMap(Collection::stream)
                .map(cv -> new FieldError(cv.getRootBeanClass().getName(), cv.getPropertyPath().toString(), cv.getInvalidValue(),
                        true, null, null, cv.getMessage()))
                .collect(Collectors.toList());
        fieldErrorList.addAll(documentSignatureParametersErrors);
        return documentSignatureParametersErrors.isEmpty();
    }

    private void validateDocumentsParams(DocumentSignatureParametersDTO[]
                                                 documentSignatureParameters, List<FieldError> fieldErrorList) {
    /*
        * we can consider a documentsParameter as valid if it has the following criterion :
            - isVisible is set to true and x and y are not null and valid.
            - isVisible is set to false and x and y are not specified.
            - isVisible is set to true and page is provided
     */

        final Set<FieldError> isVisibleErrors = Arrays.stream(documentSignatureParameters)
                .filter(params -> ((params.getVisible() && (params.getOriginX() == null || params.getOriginY() == null || params.getPageSeal() == null)) ||
                        (!params.getVisible() && (params.getOriginX() != null || params.getOriginY() != null
                                || params.getHeight() != null || params.getWidth() != null || params.getPageSeal() != null))))
                .map(params ->
                        new FieldError(getIndexOfTheObject(params, documentSignatureParameters),
                                "isVisible", getValuesFromStringRepresentation(params), false, null, null,
                                (params.getVisible() ? "isVisible is set to true and x, y or page is null or not valid!" :
                                        "isVisible is set to false and x, y, width, height or page is specified!")
                        ))
                .collect(Collectors.toSet());

        fieldErrorList.addAll(isVisibleErrors);

    }

    private String getIndexOfTheObject(DocumentSignatureParametersDTO params, DocumentSignatureParametersDTO[]
            documentSignatureParameters) {
        return "Element at index " + IntStream.range(0, documentSignatureParameters.length)
                .filter(i -> params.equals(documentSignatureParameters[i]))
                .findFirst().orElse(-1);
    }

    private String getValuesFromStringRepresentation(DocumentSignatureParametersDTO params) {
        String splitString = params.toString().split("\\(")[1];
        return splitString.substring(0, splitString.length() - 1);
    }
}
