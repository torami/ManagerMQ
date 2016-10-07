package com.disp.database;

import java.util.Map;

import javax.jms.Queue;
import javax.sql.DataSource;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.disp.demo.JmsMessageSender;

public class CamelJdbcPollingScénario {
	public static void main(String[] args) throws Exception {
	    // init spring context
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
	    // get bean from context
	    JmsMessageSender jmsMessageSender = (JmsMessageSender)ctx.getBean("jmsMessageSender");
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("dataSourceApplicationContext.xml");
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		JndiContext jndiContext = new JndiContext();
		jndiContext.bind("dataSource", dataSource);
		CamelContext camelContext = new DefaultCamelContext(jndiContext);
		try {
			camelContext.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("timer://pollTable"
							+ "?period=1s")
                    .setBody(constant("select * from signalement"))
                    .to("jdbc:dataSource")
                    .split(simple("${body}"))
                    .process(new Processor() {
						
						public void process(Exchange exchange) throws Exception {
							Map<String, Object> signalement = exchange.getIn().getBody(Map.class);
							System.out.println("Process signalement " + signalement);
							 // send to default destination 
						    jmsMessageSender.send(signalement);
						         
						   
						         
						   
						    System.out.print(signalement.get("firstname")+"aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
						    // send to a code specified destination
						    Queue queue = new ActiveMQQueue("AnotherDest");
						    jmsMessageSender.send(queue, "hello Another Message");
						   
						    // close spring application context
						    ((ClassPathXmlApplicationContext)ctx).close();
						}
					});
				}
			});
			camelContext.start();
			Thread.sleep(3000);
		} finally {
			camelContext.stop();
			context.close();
		}
	}
}
