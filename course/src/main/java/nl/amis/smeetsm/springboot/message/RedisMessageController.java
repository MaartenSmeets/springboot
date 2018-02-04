package nl.amis.smeetsm.springboot.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="messages", description="Operations pertaining to messages")
public class RedisMessageController {
	
	private static final Logger log = LoggerFactory.getLogger(RedisMessageController.class);
	
	@Autowired
	private RedisMessageService redisMessageService;
	
    @ApiOperation(value = "View a list messages",response = List.class)
	@RequestMapping(method=RequestMethod.GET,value="/redismessages")
	public List<Message> getAllMessages() {
		return redisMessageService.getAllMessages();
	}
	
    @ApiOperation(value = "Get a message by id",response = Message.class)
    @RequestMapping(method=RequestMethod.GET,value="/redismessages/{id}")
	public Message getMessage(@PathVariable Long id) {
		return redisMessageService.getMessage(id);
	}
	
    @ApiOperation(value = "Delete a message by id")
	@RequestMapping(method=RequestMethod.DELETE,value="/redismessages/{id}")
	public void deleteMessage(@PathVariable Long id) {
		redisMessageService.deleteMessage(id);
	}
    
    @ApiOperation(value = "Delete all messages")
	@RequestMapping(method=RequestMethod.DELETE,value="/redismessages")
	public void deleteMessage() {
		redisMessageService.deleteMessages();
	}
	
	
    @ApiOperation(value = "Add a message")
	@RequestMapping(method=RequestMethod.POST,value="/redismessages")
	public Message addMessage(@RequestBody Message message) {
    	log.info("Adding: "+message.toString());
		return redisMessageService.addMessage(message);
	}

    @ApiOperation(value = "Update a message")
	@RequestMapping(method=RequestMethod.PUT,value="/redismessages/{id}")
	public Message addMessage(@RequestBody Message message,@PathVariable Long id) {
    	return redisMessageService.updateMessage(message);
	}
	
}
