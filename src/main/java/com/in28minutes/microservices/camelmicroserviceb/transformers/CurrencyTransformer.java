package com.in28minutes.microservices.camelmicroserviceb.transformers;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyTransformer {
    public BigDecimal transform(CurrencyExchangeBean exchangeBean) {
        return exchangeBean.getConversionMultiple().multiply(new BigDecimal(exchangeBean.getId()));
    }
}
