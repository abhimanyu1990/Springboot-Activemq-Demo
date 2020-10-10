package poc.activemq.configuration;

import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.activemq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listner {

private static final Logger LOGGER = LoggerFactory.getLogger(Listner.class);

	
	@JmsListener(destination = "message-queue")
	public void messageReceiver(Message message) throws JMSException {
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MessageSenderTemplate messageObj = (MessageSenderTemplate)objectMessage.getObject();
            LOGGER.info("Queue receiver 1---->"+messageObj.getFromUser());
            
        }
	}
	
	
	@JmsListener(destination = "message-queue")
	public void messageReceiver2(Message message) throws JMSException {
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MessageSenderTemplate messageObj = (MessageSenderTemplate)objectMessage.getObject();
            LOGGER.info("Queue receiver 2 ---->"+messageObj.getFromUser());
          
        }
	}
	
	
}
