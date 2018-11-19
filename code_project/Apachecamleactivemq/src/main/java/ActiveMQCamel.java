import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

import javax.jms.ConnectionFactory;

public class ActiveMQCamel {

    private static String user= ActiveMQConnection.DEFAULT_USER;

    private static String password = ActiveMQConnection.DEFAULT_PASSWORD;

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws Exception{

        PropertyConfigurator.configure("C:\\apache-activemq-5.11.0\\conf\\log4j.properties");
        PropertyConfigurator.configureAndWatch("C:\\apache-activemq-5.11.0\\conf\\log4j.properties", 1000);

        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, "tcp://localhost:61616");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        System.out.println(url + " " + user + " " + password);

        context.addRoutes(new RouteBuilder(){

            public void configure() throws Exception {
                from("file:C:\\Users\\pxiao\\IdeaProjects\\Apachecamleactivemq\\temp").to("jms:queue:hoo.mq.queue");
            }

        });

        context.start();

        synchronized (ActiveMQCamel.class){
            ActiveMQCamel.class.wait();
        }
    }

}
