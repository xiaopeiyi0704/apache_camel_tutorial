import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.JmsException;

import javax.jms.MessageListener;
import javax.jms.*;

public class MessageReceiver {


    public static final String BROKER_URL = "tcp://localhost:61616";

    public static final String DESTINATION = "hoo.mq.queue";

    public static void main(String[] args) throws Exception {
        MessageReceiver.run();
    }

    public static void run() throws Exception {

        Connection connection = null;
        Session session = null;

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);

            connection = factory.createConnection();

            connection.start();

            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(DESTINATION);

            MessageConsumer consumer = session.createConsumer(destination);

            consumer.setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage txtMsg = (TextMessage) message;
                            System.out.println("Received Text message : " + txtMsg.getText());
                        } else if (message != null) {
                            BytesMessage bytesMsg = (BytesMessage) message;
                            byte[] bytes = new byte[(int) bytesMsg.getBodyLength()];
                            bytesMsg.readBytes(bytes);
                            System.out.println("Received byte message: " + new String(bytes));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });
            synchronized (MessageReceiver.class) {
                MessageReceiver.class.wait();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null!=session){
                session.close();
            }
            if(null!=connection){
                connection.close();
            }
        }
        }


}
