package poc.activemq.configuration;

import java.util.Arrays;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActivemqJmsConfiguration {
	
	@Value("${spring.activemq.broker-url}")
	private String broker_url;
	
	@Value("${spring.activemq.user}")
	private String broker_username;
	
	@Value("${spring.activemq.password}")
	private String broker_password;

	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
	    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	    connectionFactory.setBrokerURL(broker_url);
	    connectionFactory.setPassword(broker_username);
	    connectionFactory.setUserName(broker_password);
	    connectionFactory.setMaxThreadPoolSize(20);
	    connectionFactory.setTrustedPackages(Arrays.asList("poc.activemq"));
	    
	    return connectionFactory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(){
	    JmsTemplate template = new JmsTemplate();
	    template.setConnectionFactory(connectionFactory());
	    template.setPubSubDomain(false);
	    return template;
	}

	// ContainerFactory for listening from a queue
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory());
	    
	    //minimum and maximum no. of concurrent sesssion a consumer will create
	    factory.setConcurrency("1-1");
	    //pubsub domain false means receiving message from a queue
	    factory.setPubSubDomain(false);
	    return factory;
	}
	
	
	// ContainerFactory for listening from a Topic
	@Bean
	public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory,
	                                                DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    configurer.configure(factory, connectionFactory);
	    
	    //pubSubDomain is true means receiving message from Topic
	    factory.setPubSubDomain(true);
	    //minimum and maximum no. of concurrent sesssion a consumer will create
	    factory.setConcurrency("1-1");
	    return factory;
	}
	
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("message-queue");
	}
	
	
}
