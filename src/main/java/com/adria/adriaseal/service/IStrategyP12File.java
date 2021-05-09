package com.adria.adriaseal.service;

import eu.europa.esig.dss.token.Pkcs12SignatureToken;

public interface IStrategyP12File {
    Pkcs12SignatureToken getPKCS12SignatureToken();
}
