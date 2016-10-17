package com.disp.publishsubscribe;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Name of the topic from which we will receive messages from = " testt"

    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();

        // need to setClientID value, any string value you wish
        connection.setClientID("1234");

        try{
        connection.start();
        }catch(Exception e){
            System.err.println("NOT CONNECTED!!!");
        }
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("testt");

        //need to use createDurableSubscriber() method instead of createConsumer() for topic
        // MessageConsumer consumer = session.createConsumer(topic);
        MessageConsumer consumer = session.createDurableSubscriber(topic,
                "SUB1234");

        MessageListener listner = new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof ObjectMessage) {
                        ObjectMessage signalement = (ObjectMessage) message;
                        System.out.println("Received message"
                                + signalement.getObject() + "'");
                    }
                } catch (JMSException e) {
                    System.out.println("Caught:" + e);
                    e.printStackTrace();
                }
            }
        };

        consumer.setMessageListener(listner);
//        connection.close();

    }
}