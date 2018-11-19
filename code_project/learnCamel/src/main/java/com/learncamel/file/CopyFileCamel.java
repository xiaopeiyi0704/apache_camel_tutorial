package com.learncamel.file;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CopyFileCamel {

    public static void main(String[] args) {

        CamelContext camelContext=new DefaultCamelContext();
        try {
            camelContext.addRoutes(new CopyFileRoute());

            camelContext.start();

            //add the sleep to wait until the copy done, then stop
            Thread.sleep(10000);
            camelContext.stop();
        } catch(Exception e){
            e.printStackTrace();
        }





    }

}
