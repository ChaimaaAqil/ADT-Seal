package com.adria.adriaseal.service.impl;

import com.adria.adriaseal.dto.request.ClientDTO;
import com.adria.adriaseal.dto.request.DocumentDTO;
import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.exception.FileNotValidException;
import com.adria.adriaseal.exception.GenericException;
import com.adria.adriaseal.exception.MultipartFileException;
import com.adria.adriaseal.service.ICertificationServiceV1;
import com.adria.adriaseal.service.IStrategyP12File;
import com.adria.adriaseal.service.ReturnTypeStrategy;
import com.adria.adriaseal.service.ReturnTypeStrategyName;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.*;
import eu.europa.esig.dss.pades.*;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
@RequiredArgsConstructor
public class CertificationServiceImplV1 implements ICertificationServiceV1 {

    private final IStrategyP12File p12FileStrategy;
    private final PAdESService service;
    private final ReturnTypeStrategyFactory strategyFactory;
    @Resource(name = "PAdESSBaseLineEnvelopedSHA256Params")
    private PAdESSignatureParameters baseParameters;
    @Resource(name = "BaseTextParameters")
    private SignatureImageTextParameters baseTextParameters;

// cette methode permet de vérifier si la signature est visible ou non et infecte chaque document a sa methode
    @Override
    public Object certifMultipleDocuments(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParameters, ClientDTO client, ReturnTypeStrategyName returnTypeStrategyName) {
        ReturnTypeStrategy strategy = this.strategyFactory.findStrategy(returnTypeStrategyName);

        final List<InMemoryDocument> inMemoryDocuments = IntStream.range(0, documentDTO.getDocuments().length)
                .mapToObj(index -> documentSignatureParameters[index].getVisible().equals(true) ?
                        this.certifVisiblyADocument(documentDTO.getDocuments()[index], documentDTO.getSignatureImage()[0]
                                , documentSignatureParameters[index], client) :
                        this.certifInvisiblyADocument(documentDTO.getDocuments()[index]))
                .collect(Collectors.toList());
        return strategy.returnFile(inMemoryDocuments);
    }
    // cette methode permet de faire la certifications invisible des documentEntities on donnant dans ses parametes le doc à certifier

    @Override
    public InMemoryDocument certifInvisiblyADocument(MultipartFile documentToBeCertified) {
        // Get the Pkcs12 token
        Pkcs12SignatureToken token = p12FileStrategy.getPKCS12SignatureToken();
        // extract the privateKey from the token
        DSSPrivateKeyEntry privateKey = token.getKeys().get(0);
        PAdESSignatureParameters invisibleSignatureParameters = SerializationUtils.clone(baseParameters);

        invisibleSignatureParameters.setSigningCertificate(privateKey.getCertificate());
        invisibleSignatureParameters.setCertificateChain(privateKey.getCertificateChain());

        return certifDocumentService(documentToBeCertified, token, privateKey, invisibleSignatureParameters);
    }
    // cette methode permet de faire la certifications visible des documentEntities  on donnant dans ses parametes le document à certifier, l'image de signature, les parametres de documentEntities a certifier , le clientEntity

    @Override
    public InMemoryDocument certifVisiblyADocument(MultipartFile documentToBeCertified, MultipartFile signatureImage, DocumentSignatureParametersDTO documentCertificateParametersDTO, ClientDTO clientDTO) {
        // Get the Pkcs12 token
        Pkcs12SignatureToken token = p12FileStrategy.getPKCS12SignatureToken();
        // extract the privateKey from the token
        DSSPrivateKeyEntry privateKey = token.getKeys().get(0);
        // create parameters for certificate the documentEntities
        PAdESSignatureParameters visibleSignatureParameters = SerializationUtils.clone(baseParameters);
        visibleSignatureParameters.setSigningCertificate(privateKey.getCertificate());
        visibleSignatureParameters.setCertificateChain(privateKey.getCertificateChain());
        //initialize signature field parameters
        SignatureImageParameters imageParameters = createSignatureImageParameters(signatureImage, clientDTO, documentCertificateParametersDTO);
        // Adding the fieldParameters and imageParameters to the PAdESSignatureParameters
        visibleSignatureParameters.setImageParameters(imageParameters);
        // sign and return the list of documentEntities urls

        return certifDocumentService(documentToBeCertified, token, privateKey, visibleSignatureParameters);
    }
// cette methode permet de faire la certification , elle fait l'appel au service PADES de DSS
    private InMemoryDocument certifDocumentService(MultipartFile document, Pkcs12SignatureToken token, DSSPrivateKeyEntry privateKey,
                                                 PAdESSignatureParameters parameters) {
        // permission de certification
        parameters.setPermission(CertificationPermission.MINIMAL_CHANGES_PERMITTED);
        // Get the SignedInfo segment that need to be signed.
        try {

            DSSDocument toCertifDocument = new InMemoryDocument(document.getInputStream(), document.getOriginalFilename());
            ToBeSigned dataToSign = service.getDataToSign(toCertifDocument, parameters);

            DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();


            SignatureValue signatureValue = token.sign(dataToSign, digestAlgorithm, privateKey);

            return (InMemoryDocument) service.signDocument(toCertifDocument, parameters, signatureValue);

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new FileNotValidException("The signature image file might be invalid", illegalArgumentException);
        } catch (DSSException dssException) {
            dssException.printStackTrace();
            throw new FileNotValidException("This PDF file might be invalid: " + document.getOriginalFilename(), dssException);
        } catch (IndexOutOfBoundsException outOfBoundsException) {
            throw new GenericException("Page parameter needs to be less or equal than the number of pages!", outOfBoundsException);
//            "Page parameter needs to be less or equal than the number of pages!",
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotValidException(e.getLocalizedMessage(), e);
        }
    }

// cette methode permet de creer l'image de signature dans le documentEntities visible à certifier
    private SignatureImageParameters createSignatureImageParameters(MultipartFile signatureImage, ClientDTO clientDTO, DocumentSignatureParametersDTO documentCertificateParametersDTO) {
        SignatureImageParameters imageParameters = new SignatureImageParameters();
        DSSDocument image;
        try {
            image = new InMemoryDocument(signatureImage.getBytes(), signatureImage.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            throw new MultipartFileException("Couldn't use the signature image! ", e);
        }
        imageParameters.setImage(image);

        //TODO create beans for the config files

        //TODO separate the image from the signature
        SignatureFieldParameters fieldParameters = new SignatureFieldParameters();
        // the origin is the left and top corner of the page
        fieldParameters.setOriginX(documentCertificateParametersDTO.getOriginX());
        fieldParameters.setOriginY(documentCertificateParametersDTO.getOriginY());
        fieldParameters.setHeight(documentCertificateParametersDTO.getHeight() != null ? documentCertificateParametersDTO.getHeight() : 70);
        fieldParameters.setWidth(documentCertificateParametersDTO.getWidth() != null ? documentCertificateParametersDTO.getWidth() : 150);
        fieldParameters.setPage(documentCertificateParametersDTO.getPageSeal());

        final String cin = clientDTO.getCin();
        final String email = clientDTO.getEmail();
        final String gsm = clientDTO.getPhoneNumber();
        final String businessId = clientDTO.getBusinessId();

        final String organisation = clientDTO.getOrganisation();
        final SignatureImageTextParameters textParameters = new SignatureImageTextParameters();
        String organisationName = organisation.toLowerCase();


        final Date date = new Date();
        String signatureText = String.format("Digitally certified by:%n%s", organisationName);
        String businessIdText = businessId == null ? "" : String.format("%nBID:%s", businessId);
        String dateText = String.format("%n%tF %tT", date, date);

        SignatureImageTextParameters customTextParameters = SerializationUtils.clone(baseTextParameters);
        customTextParameters.setText(signatureText + businessIdText + dateText);

        imageParameters.setTextParameters(customTextParameters);
        imageParameters.setFieldParameters(fieldParameters);

        return imageParameters;
    }

}
