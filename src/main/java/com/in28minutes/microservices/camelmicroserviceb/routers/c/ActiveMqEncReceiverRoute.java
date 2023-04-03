package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@ConditionalOnProperty(value = "routers.activemq-router.enc.on", havingValue = "true")
public class ActiveMqEncReceiverRoute extends RouteBuilder {
    private static final Logger logger = getLogger(ActiveMqEncReceiverRoute.class);
    @Override
    public void configure() throws Exception {
        from("activemq:enc-queue")
                .unmarshal(createEncryptor())
                .to("log:secured-message-logger");
    }

    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "Ringhietto2009".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "Ringhietto2009".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        logger.info("The KeyStore has been loaded");
        return sharedKeyCrypto;
    }
}
