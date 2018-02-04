package nl.amis.smeetsm.springboot.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<>();
		messageRepository.findAll().forEach(messages::add);
		return messages;
	}

	public Message getMessage(Long id) {
		return messageRepository.findOne(id);
	}

	public void deleteMessage(Long id) {
		messageRepository.delete(id);
	}

	public Message addMessage(Message message) {
		messageRepository.save(message);
		return message;
	}

	public Message updateMessage(Long id, Message message) {
		messageRepository.save(message);
		return message;
	}

	public void deleteMessages() {
		messageRepository.deleteAll();
		
	}
}
