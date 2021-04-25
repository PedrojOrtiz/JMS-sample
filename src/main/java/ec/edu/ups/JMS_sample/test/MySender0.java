package ec.edu.ups.JMS_sample.test;

import ec.edu.ups.JMS_sample.utils.Utils;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.Context;
import java.util.Scanner;

public class MySender0 {

    private static String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static String DESTINATION = "jms/testQueue";

    public static void main(String[] args) {
        //Create and start connection
        try {
            //1) Create and start connection
            Context ctx = Utils.createInitialContext();

            ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
            System.out.println("Got ConnectionFactory " + CONNECTION_FACTORY);
            Destination destination = (Destination) ctx.lookup(DESTINATION);
            System.out.println("Got JMS Endpoint " + DESTINATION);

            JMSContext context = connectionFactory.createContext("user0", "user0");

            JMSProducer producer = context.createProducer();

            System.out.print("Enter message: ");
            try (Scanner sc = new Scanner(System.in)) {
                while (sc.hasNextLine()) {
                    String msg = sc.nextLine();
                    producer.setProperty("source", "user0").send(destination, msg);
                    System.out.print("Enter message: ");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
