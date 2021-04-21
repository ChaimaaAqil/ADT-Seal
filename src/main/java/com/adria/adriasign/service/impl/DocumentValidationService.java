package com.adria.adriasign.service.impl;


import com.adria.adriasign.dto.request.DocumentDTO;
import com.adria.adriasign.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriasign.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DocumentValidationService {

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
        validateDocumentsParams(documentSignatureParameters, fieldErrorList);
        validateVisibleSignatureParams(documentDTO, documentSignatureParameters, fieldErrorList, lengthField, objectName);
        if (!fieldErrorList.isEmpty()) {
            throw new ValidationException(fieldErrorList);
        }
    }

    private void validateVisibleSignatureParams(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParameters, List<FieldError> fieldErrorList, String lengthField, String objectName) {

        // The number of visible signatures should be greater or equal to the number on images
        final long numberOfVisibleSignaturesParams = Arrays.stream(documentSignatureParameters)
                .filter(DocumentSignatureParametersDTO::getIsVisibleSignature)
                .count();

        if (numberOfVisibleSignaturesParams > 0) {
            try {
                if (documentDTO.getSignatureImage() == null || documentDTO.getSignatureImage()[0].getBytes().length == 0) {
                    fieldErrorList.add(new FieldError(objectName, lengthField,
                            String.format("The number of visible signatures params equals to %d while there is no image provided!", numberOfVisibleSignaturesParams)));
                }
            } catch (IOException e) {
                fieldErrorList.add(new FieldError(objectName, lengthField, "Error validating the signatureImage param!"));
            }
        } else
            try {
                if (documentDTO.getSignatureImage() != null && documentDTO.getSignatureImage()[0].getBytes().length != 0)
                    fieldErrorList.add(new FieldError(objectName, lengthField, "signature image", true, null,
                            null, "The number of visible signatures params equals to 0, but there is a signature image provided!"));
            } catch (IOException ignored) {
                fieldErrorList.add(new FieldError(objectName, lengthField, "Error validating the signatureImage param!"));
            }
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
                .filter(params -> ((params.getIsVisibleSignature() && (params.getOriginX() == null || params.getOriginY() == null || params.getPage() == null)) ||
                        (!params.getIsVisibleSignature() && (params.getOriginX() != null || params.getOriginY() != null
                                || params.getHeight() != null || params.getWidth() != null || params.getPage() != null))))
                .map(params ->
                        new FieldError(getIndexOfTheObject(params, documentSignatureParameters),
                                "isVisible", getValuesFromStringRepresentation(params), false, null, null,
                                (params.getIsVisibleSignature().equals(true) ? "isVisible is set to true and x, y or page is null or not valid!" :
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


