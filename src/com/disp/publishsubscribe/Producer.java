package com.disp.publishsubscribe;

import java.io.Serializable;
import java.util.Map;

import javax.jms.*;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.disp.messageprocessing.Email;



public class Producer {

	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	public static void main(String[] args) throws JMSException {


		/**
		 * here we start 
		 */
		// get bean from context
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("dataSourceApplicationContext.xml");
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		JndiContext jndiContext = null;
		try {
			jndiContext = new JndiContext();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			jndiContext.bind("dataSource", dataSource);
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		CamelContext camelContext = new DefaultCamelContext(jndiContext);
		try {
			camelContext.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("timer://pollTable"
							+ "?period=3s")
					.setBody(constant("select * from signalement"))
					.to("jdbc:dataSource")
					.split(simple("${body}"))
					.process(new Processor() {

						public void process(Exchange exchange) throws Exception {
							Map<String, Object> signalement = exchange.getIn().getBody(Map.class);
							System.out.println("Process signalement " + signalement);
							//declaring a TOPIC and processing
							ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
							Connection connection = connectionFactory.createConnection();
							connection.start();

							// JMS messages are sent and received using a Session. We will
							// create here a non-transactional session object. If you want
							// to use transactions you should set the first parameter to 'true'
							Session session = connection.createSession(false,
									Session.AUTO_ACKNOWLEDGE);

							Topic topic = session.createTopic("testt");
							// send to default destination 
							MessageProducer producer = session.createProducer(topic);
							// We will send an object signalement
							ObjectMessage message = session.createObjectMessage();
							message.setObject((Serializable) signalement);
					        producer.send(message);
							System.out.println("Sent message '" + message.getObject() + "'");
							 // Send an email to the administration
						    Email mail = new Email();
						    mail.SendMyEmail("reporter.bergerlevrault@gmail.com","Snoopy12","rami.torkhani@gmail.com", " Nouvelle Réclamation");
							connection.close();


						}
					});
				}
			});
			camelContext.start();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				camelContext.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.close();
		}

		/**
		 * here we end
		 */





	}
}





