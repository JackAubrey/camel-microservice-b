package com.in28minutes.microservices.camelmicroserviceb.rest;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * very simple rest end-point did just educational scopes.
 * it is invoked by the A-Microservice
 */
@RestController
@RequestMapping("/currency")
public class CurrencyExchangeController {
    private static final Logger logger = getLogger(CurrencyExchangeController.class);

    @GetMapping(value = "/from/{from}/to/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyExchangeBean convert(@PathVariable("from") String from,
                                        @PathVariable("to") String to) {
        logger.info("Received: from[{}] to[{}] ", from, to);
        return new CurrencyExchangeBean(1001L, from, to, BigDecimal.valueOf(1.23));
    }
}
