package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "routers.activemq-router.on", havingValue = "true")
public class ActiveMqReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .log("${body}")
                .to("log:received-message-from-active-mq");
    }
}
