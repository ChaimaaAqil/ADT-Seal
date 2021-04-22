package com.adria.adriasign.service.impl;

import com.adria.adriasign.dto.request.ClientDTO;
import com.adria.adriasign.dto.request.DocumentDTO;
import com.adria.adriasign.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriasign.exception.FileNotValidException;
import com.adria.adriasign.exception.GenericException;
import com.adria.adriasign.exception.MultipartFileException;
import com.adria.adriasign.service.ICertificationServiceV3;
import com.adria.adriasign.service.IStrategyP12File;
import com.adria.adriasign.service.ReturnTypeStrategy;
import com.adria.adriasign.service.ReturnTypeStrategyName;
import com.google.common.base.CaseFormat;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignerTextHorizontalAlignment;
import eu.europa.esig.dss.enumerations.SignerTextPosition;
import eu.europa.esig.dss.model.*;
import eu.europa.esig.dss.pades.*;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
public class CertificationServiceImplV3 implements ICertificationServiceV3 {

    private final IStrategyP12File p12FileStrategy;
    private final PAdESService service;
    private final ReturnTypeStrategyFactory strategyFactory;
    @Resource(name = "PAdESSBaseLineEnvelopedSHA256Params")
    private PAdESSignatureParameters parameters;

    public CertificationServiceImplV3(IStrategyP12File p12FileStrategy, PAdESService pAdESService
            , ReturnTypeStrategyFactory strategyFactory) {
        this.p12FileStrategy = p12FileStrategy;
        this.service = pAdESService;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public Object certifMultipleDocuments(DocumentDTO documentDTO, DocumentSignatureParametersDTO[] documentSignatureParameters, ClientDTO client, ReturnTypeStrategyName returnTypeStrategyName) {
        ReturnTypeStrategy strategy = this.strategyFactory.findStrategy(returnTypeStrategyName);

        final List<InMemoryDocument> inMemoryDocuments = IntStream.range(0, documentDTO.getDocuments().length)
                .mapToObj(index -> documentSignatureParameters[index].getIsVisibleSignature().equals(true) ?
                        this.certifVisiblyADocument(documentDTO.getDocuments()[index], documentDTO.getSignatureImage()[0]
                                , documentSignatureParameters[index], client) :
                        this.certifInvisiblyADocument(documentDTO.getDocuments()[index]))
                .collect(Collectors.toList());
        return strategy.returnFile(inMemoryDocuments);
    }

    @Override
    public InMemoryDocument certifInvisiblyADocument(MultipartFile documentToBeCertified) {
        // Get the Pkcs12 token
        Pkcs12SignatureToken token = p12FileStrategy.getPKCS12SignatureToken();
        // extract the privateKey from the token
        DSSPrivateKeyEntry privateKey = token.getKeys().get(0);
        parameters.setSigningCertificate(privateKey.getCertificate());
        parameters.setCertificateChain(privateKey.getCertificateChain());

        return certifDocumentService(documentToBeCertified, token, privateKey, parameters);
    }

    @Override
    public InMemoryDocument certifVisiblyADocument(MultipartFile documentToBeCertified, MultipartFile signatureImage, DocumentSignatureParametersDTO documentCertificateParametersDTO, ClientDTO clientDTO) {
        // Get the Pkcs12 token
        Pkcs12SignatureToken token = p12FileStrategy.getPKCS12SignatureToken();
        // extract the privateKey from the token
        DSSPrivateKeyEntry privateKey = token.getKeys().get(0);
        // create parameters for certificate the documents
        parameters.setSigningCertificate(privateKey.getCertificate());
        parameters.setCertificateChain(privateKey.getCertificateChain());
        //initialize signature field parameters
        SignatureImageParameters imageParameters = createSignatureImageParameters(signatureImage, clientDTO, documentCertificateParametersDTO);
        // Adding the fieldParameters and imageParameters to the PAdESSignatureParameters
        parameters.setImageParameters(imageParameters);
        // sign and return the list of documents urls

        return certifDocumentService(documentToBeCertified, token, privateKey, parameters);
    }

    private InMemoryDocument certifDocumentService(MultipartFile document, Pkcs12SignatureToken token, DSSPrivateKeyEntry privateKey,
                                                 PAdESSignatureParameters parameters) {
        // Get the SignedInfo segment that need to be signed.
        try {

            DSSDocument toCertifDocument = new InMemoryDocument(document.getInputStream(), document.getOriginalFilename());
            ToBeSigned dataToSign = service.getDataToSign(toCertifDocument, parameters);


            DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();
            // Add Permission Certification
            parameters.setPermission(CertificationPermission.MINIMAL_CHANGES_PERMITTED);


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
        fieldParameters.setPage(documentCertificateParametersDTO.getPage());

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

        DSSFont font = new DSSJavaFont("monospaced", 8);

        textParameters.setText(signatureText + businessIdText + dateText);
        textParameters.setFont(font);
        textParameters.setSignerTextPosition(SignerTextPosition.BOTTOM);
        textParameters.setSignerTextHorizontalAlignment(SignerTextHorizontalAlignment.LEFT);
        textParameters.setTextColor(Color.BLACK);

        imageParameters.setTextParameters(textParameters);
        imageParameters.setFieldParameters(fieldParameters);

        return imageParameters;
    }

}
