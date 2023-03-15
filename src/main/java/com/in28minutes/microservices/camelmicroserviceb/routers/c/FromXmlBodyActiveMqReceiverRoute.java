package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import com.in28minutes.microservices.camelmicroserviceb.bo.CurrencyExchangeBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "routers.file.xml.from.activemq-router.on", havingValue = "true")
public class FromXmlBodyActiveMqReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:xml-activemq-queue")
                .log("before unmarshalling ${body}")
                .unmarshal()
                .jacksonXml(CurrencyExchangeBean.class)
                .to("log:received-xml-message-from-activemq");
    }
}
