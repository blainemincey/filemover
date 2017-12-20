package com.bmincey.filemover;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JmsRoute extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(JmsRoute.class);

    @Override
    public void configure() throws Exception {
        from("{{inbound.endpoint}}")
                .log(LoggingLevel.INFO, logger, "Received Message")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String messageString = exchange.getIn().getBody(String.class);

                        logger.info("Message: {}", messageString);
                    }
                })
                .to("{{outbound.endpoint}}")
                .log(LoggingLevel.INFO, logger, "Message Sent.");
    }
}
