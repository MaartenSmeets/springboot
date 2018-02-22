package nl.amis.smeetsm.springboot.person;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.cloud.cache.metrics.CacheMetrics;

@Service
public class PersonService {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonCacheRepositoryImpl personRepository;
	
	@Autowired
	private PersonSlowRepositoryImpl personSlowRepository;
	
	public List<Person> getAllPersons() {
		log.info("getAllPersons called");
		return personSlowRepository.findAllPersons();
	}

	public Person getPerson(Long id) {
		log.info("getPerson called with: "+id.toString());
		return personRepository.findPerson(id);
	}

	public void deletePerson(Long id) {
		log.info("deletePerson called with: "+id.toString());
		personSlowRepository.delete(id);
		personRepository.delete(id);
	}

	public Person addPerson(Person person) {
		log.info("addPerson called with: "+person.toString());
		personSlowRepository.add(person);
		personRepository.add(person);
		return person;
	}

	public Person updatePerson(Long id, Person person) {
		log.info("updatePerson called with: "+person.toString());
		personSlowRepository.update(person);
		personRepository.update(person);
		return person;
	}

	public void deletePersons() {
		log.info("deletePersons called");
		personSlowRepository.deleteAll();
		personRepository.deleteAll();
	}

	public CacheMetrics getMetrics() {
		log.info("getMetrics called");
		return personRepository.getMetrics();
	}
}
