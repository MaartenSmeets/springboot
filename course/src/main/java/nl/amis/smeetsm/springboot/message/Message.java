package nl.amis.smeetsm.springboot.message;

import java.io.IOException;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public void setId(Long id) {
		this.id = id;
	}

	private Long timestamp;
	private String text;

	public Message() {
		this.timestamp=Instant.now().toEpochMilli();
		this.text="";
	}

	
	public Message(String text) {
		this.timestamp=Instant.now().toEpochMilli();
		this.text=text;
	}
	
	public Message(Long id,String text,Long timestamp) {
		this.timestamp=timestamp;
		this.text=text;
		this.id=id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
    public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode actualObj = mapper.createObjectNode();
		actualObj.put("id", id);
		actualObj.put("text", text);
		actualObj.put("timestamp", timestamp);
		return actualObj.toString();
    }
	
	public static Message fromJSONString(String JSONString) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode actualObj = mapper.readTree(JSONString);
	    return new Message(actualObj.get("id").asLong(),actualObj.get("text").asText(),actualObj.get("timestamp").asLong());
	}
}
