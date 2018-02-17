package nl.amis.smeetsm.springboot.person;

import java.util.List;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.cloud.cache.basic.Cache;
import com.oracle.cloud.cache.metrics.CacheMetrics;

@Repository
public class PersonCacheRepositoryImpl implements PersonRepository {

	private final Logger log = LoggerFactory.getLogger(PersonCacheRepositoryImpl.class);
	
	private Cache<Person> CACHE;
	
	@Override
	public List<Person> findAllPersons() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Person person) {
		log.info("add called for "+person.getId().toString());
		CACHE.put(person.getId().toString(), person);
	}

	@Override
	public void update(Person person) {
		log.info("update called for "+person.getId().toString());
		CACHE.put(person.getId().toString(), person);
	}

	@Override
	public void delete(Long id) {
		log.info("delete called for "+id.toString());
		CACHE.remove(id.toString());
	}

	@Override
	public void deleteAll() {
		log.info("deleteAll called");
		CACHE.clear();
	}

	@Override
	public Person findPerson(Long id) {
		log.info("findPerson called for "+id.toString());
		Person person = CACHE.get(id.toString());
		return person;
	}

	public CacheMetrics getMetrics() {
		return CACHE.getMetrics();
	}

	public void setCache(PersonCache personCache) {
		log.info("setCache called");
		CACHE=personCache.getCACHE();
	}
}
