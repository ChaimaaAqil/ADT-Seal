package com.adria.adriasign.configuration.beans;

import eu.europa.esig.dss.enumerations.*;
import eu.europa.esig.dss.pades.DSSFont;
import eu.europa.esig.dss.pades.DSSJavaFont;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.SignatureImageTextParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.pdf.pdfbox.PdfBoxNativeObjectFactory;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class BeansConfig {
    @Bean
    public PAdESService getPAdESService() {
        return  new PAdESService(new CommonCertificateVerifier());
    }

    @Bean(name = "PAdESSBaseLineEnvelopedSHA256Params")
    public PAdESSignatureParameters getPAdESSBaseLineEnvelopedSHA256Parameters() {
        PAdESSignatureParameters parameters = new PAdESSignatureParameters();
        parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
        parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);
        parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
        return parameters;
    }
    @Bean(name = "BaseTextParameters")
    public SignatureImageTextParameters getBaseTextParameters() {
        SignatureImageTextParameters signatureImageTextParameters = new SignatureImageTextParameters();
        DSSFont font = new DSSJavaFont("monospaced", 8);
        signatureImageTextParameters.setFont(font);
        signatureImageTextParameters.setSignerTextPosition(SignerTextPosition.BOTTOM);
        signatureImageTextParameters.setSignerTextHorizontalAlignment(SignerTextHorizontalAlignment.LEFT);
        signatureImageTextParameters.setTextColor(Color.BLACK);
        return signatureImageTextParameters;
    }
    @Bean
    public Tika getTikaValidator() {
        return new Tika();
    }


}
