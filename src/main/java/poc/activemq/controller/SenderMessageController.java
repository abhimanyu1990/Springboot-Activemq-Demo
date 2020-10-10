package poc.activemq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import poc.activemq.configuration.MessageSenderTemplate;
import poc.activemq.configuration.Producer;

@RestController
public class SenderMessageController {
	
	@Autowired
	Producer producer;
	
	@PostMapping("/api/send/queue")
	public void sendMessage( @RequestBody MessageSenderTemplate message) {
		producer.sendMessageToQueue(message);
	 }
	
	
	@PostMapping("/api/send/topic")
	public void sendMessageTopic( @RequestBody MessageSenderTemplate message) {
		producer.sendMessageToTopic(message);
	 }

}
