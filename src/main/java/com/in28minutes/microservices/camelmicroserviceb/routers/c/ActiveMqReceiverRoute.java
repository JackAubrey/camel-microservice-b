package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .log("${body}")
                .to("log:received-message-from-active-mq");
    }
}
