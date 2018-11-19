package com.learncamel.file;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;

public class CopyFileRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
            //if there is no camel/outbox directory, camel will create automatically
           from("file:C:\\Users\\pxiao\\Desktop\\camel\\inbox?noop=true")
           .to("file:C:\\Users\\pxiao\\Desktop\\camel\\outbox");
    }

}
