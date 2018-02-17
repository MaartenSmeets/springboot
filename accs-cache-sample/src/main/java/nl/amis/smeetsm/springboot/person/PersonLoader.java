package nl.amis.smeetsm.springboot.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.oracle.cloud.cache.basic.CacheLoader;

import nl.amis.smeetsm.springboot.AccsCacheSampleApplication;

public class PersonLoader implements CacheLoader<Person> {

	private static final Logger log = LoggerFactory.getLogger(PersonLoader.class);
	
	@Override
	public Person load(String id) {
		log.info("Load called with "+id);
		return new Person(Long.valueOf(id),"No Person");		
	}

}
