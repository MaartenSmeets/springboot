package nl.amis.smeetsm.springboot.person;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.oracle.cloud.cache.metrics.CacheMetrics;

@Repository("SlowRepository")
public class PersonSlowRepositoryImpl implements PersonRepository {
	private static final Logger log = LoggerFactory.getLogger(PersonSlowRepositoryImpl.class);
	private static final long WAITTIME = 2000;
	
	ArrayList<Person> personList = new ArrayList<Person>();
	
	public PersonSlowRepositoryImpl() {
		log.info("Constructor called");
		/*Collections.addAll(personList,
				new Person(new Long(1),"Maarten"),
				new Person(new Long(2),"Jack"),
				new Person(new Long(3),"John")); */
	}
	
	private void waitawhile() {
		try {
			Thread.sleep(WAITTIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Person> findAllPersons() {
		waitawhile();
		log.info("findAllPersons called");
		return personList;
	}

	@Override
	public void add(Person person) {
		waitawhile();
		log.info("Add called");
		personList.add(person);
	}

	@Override
	public void update(Person person) {
		waitawhile();
		log.info("Update called");
		for (int i = 0; i< personList.size(); i++) {
			if (personList.get(i).getId().equals(person.getId())) {
				personList.set(i,person);
			}
		}
	}

	@Override
	public void delete(Long id) {
		waitawhile();
		log.info("Delete called");
		for (int i = 0; i< personList.size(); i++) {
			if (personList.get(i).getId().equals(id)) {
				personList.remove(i);
			}
		}
	}

	@Override
	public void deleteAll() {
		waitawhile();
		log.info("DeleteAll called");
		personList = new ArrayList<Person>();
	}

	@Override
	public Person findPerson(Long id) {
		waitawhile();
		log.info("FindPerson called");
		for (int i = 0; i< personList.size(); i++) {
			if (personList.get(i).getId().equals(id)) {
				return personList.get(i);
			}
		}
		return null;
	}

	@Override
	public CacheMetrics getMetrics() {
		throw new UnsupportedOperationException();
	}

}
