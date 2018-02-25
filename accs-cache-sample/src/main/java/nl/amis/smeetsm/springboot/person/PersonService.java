package nl.amis.smeetsm.springboot.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.cloud.cache.metrics.CacheMetrics;

@Service
public class PersonService {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	@Qualifier("CacheRepository")
	private PersonRepository personCacheRepository;
	
	@Autowired
	@Qualifier("SlowRepository")
	private PersonRepository personSlowRepository;
	
	public List<Person> getAllPersons() {
		log.info("getAllPersons called");
		return personSlowRepository.findAllPersons();
	}

	public Person getPerson(Long id) {
		log.info("getPerson called with: "+id.toString());
		return personCacheRepository.findPerson(id);
	}

	public void deletePerson(Long id) {
		log.info("deletePerson called with: "+id.toString());
		personSlowRepository.delete(id);
		personCacheRepository.delete(id);
	}

	public Person addPerson(Person person) {
		log.info("addPerson called with: "+person.toString());
		personSlowRepository.add(person);
		personCacheRepository.add(person);
		return person;
	}

	public Person updatePerson(Long id, Person person) {
		log.info("updatePerson called with: "+person.toString());
		personSlowRepository.update(person);
		personCacheRepository.update(person);
		return person;
	}

	public void deletePersons() {
		log.info("deletePersons called");
		personSlowRepository.deleteAll();
		personCacheRepository.deleteAll();
	}

	public CacheMetrics getMetrics() {
		log.info("getMetrics called");
		return personCacheRepository.getMetrics();
	}
}
