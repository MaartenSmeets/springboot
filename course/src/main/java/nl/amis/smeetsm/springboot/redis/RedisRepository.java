package nl.amis.smeetsm.springboot.redis;

import java.util.Map;

import nl.amis.smeetsm.springboot.message.Message;

public interface RedisRepository {
    Map<Object, Object> findAllMessages();
    void add(Message message);
    void update(Message message);
    void delete(Long id);
    void deleteAll();
    Message findMessage(Long id);
    
}