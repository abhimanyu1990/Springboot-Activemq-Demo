package poc.activemq.configuration;



import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue queue;
	
	
	
	public void sendMessageToQueue( final MessageSenderTemplate message) {
		
		jmsTemplate.send(queue,new MessageCreator() {

			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(message);
				return objectMessage;
			}
		});
	}
	
	
	public void sendMessageToTopic( final MessageSenderTemplate message) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.send("message-topic",new MessageCreator() {

			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(message);
				return objectMessage;
			}
		});
	}	

}
