package nl.amis.smeetsm.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.listener.ChannelTopic;

import nl.amis.smeetsm.springboot.redis.MessagePublisher;
import nl.amis.smeetsm.springboot.redis.MessagePublisherImpl;
import nl.amis.smeetsm.springboot.redis.MessageSubscriber;

@Configuration
public class RedisConfig {

	@Value("${redis.topic.boot}")
	private String redisTopic;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new MessageSubscriber());
	}

	@Bean
	MessagePublisher redisPublisher() {
		return new MessagePublisherImpl(redisTemplate(), new ChannelTopic(redisTopic));
	}
	
	@Bean
	ChannelTopic redisChannelTopic() {
		return new ChannelTopic(redisTopic);
	}
	

}
