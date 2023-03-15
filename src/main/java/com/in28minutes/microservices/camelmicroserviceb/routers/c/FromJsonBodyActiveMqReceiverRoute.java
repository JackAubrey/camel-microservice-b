package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.slf4j.LoggerFactory.getLogger;

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

@Component
class CurrencyTransformer  {
   public BigDecimal transform(CurrencyExchangeBean exchangeBean) {
       return exchangeBean.getConversionMultiple().multiply(new BigDecimal(exchangeBean.getId()));
   }
}

@Component
class CurrencyProcessor implements Processor {
    private static final Logger logger = getLogger(CurrencyProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        CurrencyExchangeBean exchangeBean = exchange.getMessage().getBody(CurrencyExchangeBean.class);
        BigDecimal result = exchangeBean.getConversionMultiple().multiply(new BigDecimal(1));

        logger.info("From 1 {} is equals to {} {}", exchangeBean.getFrom(), result, exchangeBean.getTo());
    }
}

