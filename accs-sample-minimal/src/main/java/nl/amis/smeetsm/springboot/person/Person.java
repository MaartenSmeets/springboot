package nl.amis.smeetsm.springboot.person;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Person {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Person(Long id,String name) {
		this.id=id;
		this.name=name;
	}
	
	public Person() {
		this.id=new Long(0);
		this.name="";
	}
	
	private Long id;
	private String name;
	
	@Override
    public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode actualObj = mapper.createObjectNode();
		actualObj.put("id", id);
		actualObj.put("name", name);
		return actualObj.toString();
    }
	
	public static Person fromJSONString(String JSONString) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode actualObj = mapper.readTree(JSONString);
	    return new Person(actualObj.get("id").asLong(),actualObj.get("name").asText());
	}
}
