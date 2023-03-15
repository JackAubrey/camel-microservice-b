package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import com.in28minutes.microservices.camelmicroserviceb.processors.CurrencyProcessor;
import com.in28minutes.microservices.camelmicroserviceb.transformers.CurrencyTransformer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "routers.file.json.from.activemq-router.on", havingValue = "true")
public class FromJsonBodyActiveMqReceiverRoute extends RouteBuilder {
    private final CurrencyProcessor currencyProcessor;
    private final CurrencyTransformer currencyTransformer;

    public FromJsonBodyActiveMqReceiverRoute(CurrencyProcessor currencyProcessor, CurrencyTransformer currencyTransformer) {
        this.currencyProcessor = currencyProcessor;
        this.currencyTransformer = currencyTransformer;
    }

    @Override
    public void configure() throws Exception {
        from("activemq:json-activemq-queue")
                .log("before deserialization ${body}")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchangeBean.class)
                .log("after deserialization ${body}")
                .process(currencyProcessor)
                .bean(currencyTransformer, "transform")
                .log("after transformation ${body}")
                .to("log:received-json-message-from-active-mq");
    }
}

