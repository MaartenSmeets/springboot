package nl.amis.smeetsm.springboot.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.amis.smeetsm.springboot.redis.RedisRepository;

@Service
public class RedisMessageService {

	@Autowired
	private RedisRepository redisRepository;
	
	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<>();
		Message message;
		Long id;
		for (Object object : redisRepository.findAllMessages().keySet()) {
			id = (Long) object;
			message=new Message(object.toString());
			message.setId(id);
			messages.add(message);
		}
		return messages;
	}

	public Message getMessage(Long id) {
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
