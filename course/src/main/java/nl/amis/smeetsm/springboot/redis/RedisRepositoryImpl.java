package nl.amis.smeetsm.springboot.redis;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.amis.smeetsm.springboot.message.Message;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

	private static final Logger log = LoggerFactory.getLogger(RedisRepositoryImpl.class);

	private static final String KEY = "test";
	private RedisTemplate<String, Object> redisTemplate;
	private BoundHashOperations hashOperations;

	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.boundHashOps(KEY);
	}

	public void add(final Message message) {
		log.info("Adding: " + message.toString());
		hashOperations.put(message.getId(), message.toString());
	}

	public void delete(final Long id) {
		hashOperations.delete(id);
		
	}

	public Message findMessage(final Long id) throws JsonProcessingException, IOException {
		return Message.fromJSONString(hashOperations.get(id).toString());
	}

	public List<Message> findAllMessages() throws JsonProcessingException, IOException {
		Long id;
		List<Message> messages = new ArrayList<Message>();
		for (Object ido : hashOperations.keys()) {
			messages.add(Message.fromJSONString((hashOperations.get(ido)).toString()));
		}
		return messages;
	}

	public void deleteAll() {
		hashOperations.expireAt(new Date(Instant.now().toEpochMilli()-1));
	}

	@Override
	public void update(Message message) {
		hashOperations.put(message.getId(), message.toString());
	}
}
