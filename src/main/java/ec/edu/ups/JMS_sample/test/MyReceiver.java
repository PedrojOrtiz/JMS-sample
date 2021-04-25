package ec.edu.ups.JMS_sample.test;

import ec.edu.ups.JMS_sample.utils.Utils;

import javax.jms.*;
import javax.naming.Context;

public class MyReceiver {

    private static String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static String DESTINATION = "jms/testQueue";

    public static void main(String[] args) {
        try {
            //1) Create and start connection
            Context ctx = Utils.createInitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            System.out.println("Got ConnectionFactory " + CONNECTION_FACTORY);
            Destination destination = (Destination) ctx.lookup(DESTINATION);
            System.out.println("Got JMS Endpoint " + DESTINATION);

            JMSContext context = connectionFactory.createContext("user1", "user1");

            JMSConsumer consumer = context.createConsumer(destination);

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        System.out.print(String.format("\n" + ((TextMessage) message).getStringProperty("source") + ": %s", ((TextMessage) message).getText()));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
