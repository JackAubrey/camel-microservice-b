package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import com.in28minutes.microservices.camelmicroserviceb.processors.CurrencyProcessor;
import com.in28minutes.microservices.camelmicroserviceb.transformers.CurrencyTransformer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "routers.file.xml.from.activemq-router.on", havingValue = "true")
public class FromXmlBodyActiveMqReceiverRoute extends RouteBuilder {
    private final CurrencyProcessor currencyProcessor;
    private final CurrencyTransformer currencyTransformer;

    public FromXmlBodyActiveMqReceiverRoute(CurrencyProcessor currencyProcessor, CurrencyTransformer currencyTransformer) {
        this.currencyProcessor = currencyProcessor;
        this.currencyTransformer = currencyTransformer;
    }

    @Override
    public void configure() throws Exception {
        from("activemq:xml-activemq-queue")
                .log("before unmarshalling ${body}")
                .unmarshal()
                .jacksonXml(CurrencyExchangeBean.class)
                .log("after unmarshalling ${body}")
                .process(currencyProcessor)
                .bean(currencyTransformer, "transform")
                .log("after transformation ${body}")
                .to("log:received-xml-message-from-activemq");
    }
}
