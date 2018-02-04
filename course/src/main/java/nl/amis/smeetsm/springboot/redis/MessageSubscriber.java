package nl.amis.smeetsm.springboot.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import nl.amis.smeetsm.springboot.message.MessageController;

@Service
public class MessageSubscriber implements MessageListener {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSubscriber.class);
	
	@Autowired
	private MessageController messageController;
	  
    public void onMessage(final Message message, final byte[] pattern) {
    	//messageController.addMessage(new nl.amis.smeetsm.springboot.message.Message(message.getBody().toString()));
        LOGGER.info("Message received: " + new String());
    }

}