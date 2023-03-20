package com.in28minutes.microservices.camelmicroserviceb.routers.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "routers.kafka-router.on", havingValue = "true")
public class KafkaReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:myKafkaTopic")
                .to("log:received topic ${body}");
    }
}
