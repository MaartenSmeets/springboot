package nl.amis.smeetsm.springboot.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.amis.smeetsm.springboot.message.Message;

@Service
public class RedisMessageService {

	@Autowired
	private RedisRepository redisRepository;
	
	public List<Message> getAllMessages() throws JsonProcessingException, IOException {
		return redisRepository.findAllMessages();
	}

	public Message getMessage(Long id) throws JsonProcessingException, IOException {
		return redisRepository.findMessage(id);
	}

	public void deleteMessage(Long id) {
		redisRepository.delete(id);
	}

	public Message addMessage(Message message) {
		redisRepository.add(message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		redisRepository.update(message);
		return message;
	}

	public void deleteMessages() {
		redisRepository.deleteAll();
	}
}
