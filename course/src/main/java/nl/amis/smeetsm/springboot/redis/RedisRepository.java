package nl.amis.smeetsm.springboot.redis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.amis.smeetsm.springboot.message.Message;

public interface RedisRepository {
    List<Message> findAllMessages() throws JsonProcessingException, IOException;
    void add(Message message);
    void update(Message message);
    void delete(Long id);
    void deleteAll();
    Message findMessage(Long id) throws JsonProcessingException, IOException;
    
}