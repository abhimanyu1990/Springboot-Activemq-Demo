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
public class TopicListner {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicListner.class);
	private CountDownLatch latch = new CountDownLatch(2);

	public CountDownLatch getLatch() {
		return latch;
	}
	
	@JmsListener(destination = "message-topic",containerFactory="topicListenerFactory")
	public void messageReceiverTopic(Message message) throws JMSException {
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MessageSenderTemplate messageObj = (MessageSenderTemplate)objectMessage.getObject();
            LOGGER.info("topic receiver 1 ---->"+messageObj.getFromUser());
            latch.countDown();
        }
	}
	
	@JmsListener(destination = "message-topic", containerFactory="topicListenerFactory")
	public void messageReceiverTopic2(Message message) throws JMSException {
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MessageSenderTemplate messageObj = (MessageSenderTemplate)objectMessage.getObject();
            LOGGER.info("topic receiver 2 ---->"+messageObj.getFromUser());
            latch.countDown();
        }
	}
	

}
