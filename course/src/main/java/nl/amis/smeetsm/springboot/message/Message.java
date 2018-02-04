package nl.amis.smeetsm.springboot.message;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
		
		return String.format(
                "{\"id\":%d, \"text\":'%s', \"timestamp\":%d}",
                id, text, timestamp);
    }
}
