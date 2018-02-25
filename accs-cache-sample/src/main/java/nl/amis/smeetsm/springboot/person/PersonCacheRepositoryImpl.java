package nl.amis.smeetsm.springboot.person;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.cloud.cache.basic.Cache;
import com.oracle.cloud.cache.basic.LocalSessionProvider;
import com.oracle.cloud.cache.basic.RemoteSessionProvider;
import com.oracle.cloud.cache.basic.SessionProvider;
import com.oracle.cloud.cache.basic.options.Expiry;
import com.oracle.cloud.cache.basic.options.Transport;
import com.oracle.cloud.cache.metrics.CacheMetrics;

@Repository("CacheRepository")
public class PersonCacheRepositoryImpl implements PersonRepository {

	private final Logger log = LoggerFactory.getLogger(PersonCacheRepositoryImpl.class);
	
	@Autowired
	private PersonLoader personLoader;
	
	@Autowired
	private PersonSerializer personSerializer;
	
	private final String CACHE_NAME = "TestCache";
	private Cache<Person> CACHE;

	
	@PostConstruct
	public void init() {
		log.info("Init called");
		if (personLoader == null) {
			log.error("personLoader null!");
		}
		
		if (personSerializer == null) {
			log.error("personSerializer null!");
		}
		
		String protocolName = Optional.ofNullable(System.getenv("CACHING_PROTOCOL")).orElse("GRPC");
		log.info("Protocol - " + protocolName);

		String port = protocolName.equals("REST") ? "8080" : "1444";
		String cacheUrlSuffix = protocolName.equals("REST") ? "ccs" : "";

		Transport transport = protocolName.equals("REST") ? Transport.rest() : Transport.grpc();
		log.info("Transport - " + transport.getType().name());

		String cacheHost = System.getenv("CACHING_INTERNAL_CACHE_URL");
		String expiry = Optional.ofNullable(System.getenv("EXPIRY")).orElse("60"); // defaults to 5 seconds
		log.info("Expiry - " + expiry);
		
		SessionProvider sessionProvider;
		
		if (cacheHost == null) {
			log.info("Cachehost empty, using LocalSessionProvider");
			sessionProvider = new LocalSessionProvider();
			//CACHE = sessionProvider.createSession().getCache(CACHE_NAME, new PersonLoader(),
			//		Expiry.of(Integer.valueOf(expiry), TimeUnit.SECONDS), new PersonSerializer());
			CACHE = sessionProvider.createSession().getCache(CACHE_NAME, personLoader,
					Expiry.never(), personSerializer);
			
		} else {
			String cacheUrl = "http://" + cacheHost + ":" + port + "/" + cacheUrlSuffix;
			log.info("Cache URL - " + cacheUrl);
			sessionProvider = new RemoteSessionProvider(cacheUrl);
			//CACHE = sessionProvider.createSession(transport).getCache(CACHE_NAME, new PersonLoader(),
			//		Expiry.of(Integer.valueOf(expiry), TimeUnit.SECONDS), new PersonSerializer());
			CACHE = sessionProvider.createSession(transport).getCache(CACHE_NAME, personLoader,
					Expiry.never(), personSerializer);
		}
		
		if (CACHE == null) {
			log.info("CACHE is null");
		}
		log.info("Init done");
	}
	
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
}
