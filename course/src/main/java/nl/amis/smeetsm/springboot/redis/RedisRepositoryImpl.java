package nl.amis.smeetsm.springboot.redis;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nl.amis.smeetsm.springboot.message.Message;
import nl.amis.smeetsm.springboot.message.RedisMessageController;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

	private static final Logger log = LoggerFactory.getLogger(RedisRepositoryImpl.class);

	private static final String KEY = "test";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	public void add(final Message message) {
		log.info("Adding: " + message.toString() + " to " + KEY);
		hashOperations.put(KEY, message.getId(), message.toString());
	}

	public void delete(final Long id) {
		hashOperations.delete(KEY, id);
	}

	public Message findMessage(final Long id) {
		return (Message) hashOperations.get(KEY, id);
	}

	public Map<Object, Object> findAllMessages() {
		return hashOperations.entries(KEY);
	}

	public void deleteAll() {
		for (Object key : hashOperations.keys(KEY)) {
			hashOperations.delete(key);
		}
	}

	@Override
	public void update(Message message) {
		hashOperations.put(KEY, message.getId(), message.toString());

	}
}
