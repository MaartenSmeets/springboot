package nl.amis.smeetsm.springboot.person;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonRepositoryImpl personRepository;
	
	public List<Person> getAllPersons() {
		log.info("getAllPersons called");
		return personRepository.findAllPersons();
	}

	public Person getPerson(Long id) {
		log.info("getPerson called with: "+id.toString());
		return personRepository.findPerson(id);
	}

	public void deletePerson(Long id) {
		log.info("deletePerson called with: "+id.toString());
		personRepository.delete(id);
	}

	public Person addPerson(Person person) {
		log.info("addPerson called with: "+person.toString());
		personRepository.add(person);
		return person;
	}

	public Person updatePerson(Long id, Person person) {
		log.info("updatePerson called with: "+person.toString());
		personRepository.update(person);
		return person;
	}

	public void deletePersons() {
		log.info("deletePersons called");
		personRepository.deleteAll();
	}
}
