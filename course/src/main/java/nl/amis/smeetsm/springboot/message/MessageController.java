package nl.amis.smeetsm.springboot.message;

import java.util.List;

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
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
    @ApiOperation(value = "View a list messages",response = List.class)
	@RequestMapping(method=RequestMethod.GET,value="/messages")
	public List<Message> getAllMessages() {
		return messageService.getAllMessages();
	}
	
    @ApiOperation(value = "Get a message by id",response = Message.class)
    @RequestMapping(method=RequestMethod.GET,value="/messages/{id}")
	public Message getMessage(@PathVariable Long id) {
		return messageService.getMessage(id);
	}
	
    @ApiOperation(value = "Delete a message by id")
	@RequestMapping(method=RequestMethod.DELETE,value="/messages/{id}")
	public void deleteMessage(@PathVariable Long id) {
		messageService.deleteMessage(id);
	}
    
    @ApiOperation(value = "Delete all messages")
	@RequestMapping(method=RequestMethod.DELETE,value="/messages")
	public void deleteMessage() {
		messageService.deleteMessages();
	}
	
	
    @ApiOperation(value = "Add a message")
	@RequestMapping(method=RequestMethod.POST,value="/messages")
	public void addMessage(@RequestBody Message Message) {
		messageService.addMessage(Message);
	}

    @ApiOperation(value = "Update a message")
	@RequestMapping(method=RequestMethod.PUT,value="/messages/{id}")
	public void addMessage(@RequestBody Message Message,@PathVariable Long id) {
		messageService.updateMessage(id,Message);
	}
	
}
