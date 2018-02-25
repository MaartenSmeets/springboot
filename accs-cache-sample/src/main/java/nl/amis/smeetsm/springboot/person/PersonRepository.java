package nl.amis.smeetsm.springboot.person;

import java.util.List;

import com.oracle.cloud.cache.metrics.CacheMetrics;

public interface PersonRepository {
	List<Person> findAllPersons();

	void add(Person person);

	void update(Person person);

	void delete(Long id);

	void deleteAll();

	Person findPerson(Long id);

	CacheMetrics getMetrics();
}
