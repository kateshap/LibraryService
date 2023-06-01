package com.example.libraryservice.router;

import com.example.libraryservice.libobjects.ObjectFactory;
import com.example.libraryservice.libobjects.LibXML;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;

@Component
public class RequestRouter extends RouteBuilder {
    @Value("${kafka-requests-path}")
    private String from_path;

    @Override
    public void configure() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        JaxbDataFormat jaxb = new JaxbDataFormat(jaxbContext);

        onException(UnmarshalException.class)
                .handled(true)
                .setBody(simple("<status>error</status><message>fail</message>"))
                .to("direct:status")
                .to("direct:metrics_router_increment_fail_messages")
                .to("direct:metrics_router_stop_timer");

        from(from_path)
                .to("direct:metrics_router_increment_total_messages")
                .to("direct:metrics_router_start_timer")
                .unmarshal(jaxb)
                .choice()
                    .when(body().isInstanceOf(LibXML.class))
                    .log("Message received from Kafka : ${body}")
                    .log(" on the topic ${headers[kafka.TOPIC]}")
                    .to("direct:save_to_db")
                .otherwise()
                    .setBody(simple("<status>error</status><message>XML data isn't instance of Book</message>"))
                    .to("direct:status")
                    .to("direct:metrics_router_increment_fail_messages")
                    .to("direct:metrics_router_stop_timer");

    }
}
