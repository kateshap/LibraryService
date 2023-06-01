package com.example.libraryservice.router;

import com.example.libraryservice.libobjects.LibBD;
import com.example.libraryservice.libobjects.LibJSON;
import com.example.libraryservice.libobjects.LibXML;
import com.example.libraryservice.mapper.LibMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveRouter extends RouteBuilder {
    private final LibMapper mapper;

    @Override
    public void configure() {
        from("direct:save_to_db")
                .process(exchange -> {
                    LibXML in = exchange.getIn().getBody(LibXML.class);
                    LibBD book = mapper.mapGenerated(in);

                    exchange.getMessage().setBody(book, LibBD.class);
                })
                .log("Saving ${body} to database")
                .to("jpa:com.example.libraryservice.libobjects.LibBD")
                .process(exchange -> {
                    LibBD in = exchange.getIn().getBody(LibBD.class);
                    LibJSON book = mapper.mapWithoutId(in);

                    exchange.getMessage().setBody(book, LibJSON.class);
                })
                .marshal().json(JsonLibrary.Jackson)
                .log("Sending ${body} to kafka")
                .to("kafka:results?brokers=localhost:9092")
                .setBody(simple("<status>ok</status>"))
                .to("direct:status")
                .to("direct:metrics_router_increment_success_messages")
                .to("direct:metrics_router_stop_timer");
    }
}
