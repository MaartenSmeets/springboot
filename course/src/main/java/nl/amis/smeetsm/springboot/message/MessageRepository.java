package nl.amis.smeetsm.springboot.message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository  extends CrudRepository<Message, Long>{

}
