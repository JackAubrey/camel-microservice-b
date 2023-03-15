package com.in28minutes.microservices.camelmicroserviceb.processors;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CurrencyProcessor implements Processor {
    private static final Logger logger = getLogger(CurrencyProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        CurrencyExchangeBean exchangeBean = exchange.getMessage().getBody(CurrencyExchangeBean.class);
        BigDecimal result = exchangeBean.getConversionMultiple().multiply(new BigDecimal(1));

        logger.info("From 1 {} is equals to {} {}", exchangeBean.getFrom(), result, exchangeBean.getTo());
    }
}
