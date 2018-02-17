package nl.amis.smeetsm.springboot.person;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.cloud.cache.basic.Cache;
import com.oracle.cloud.cache.basic.LocalSessionProvider;
import com.oracle.cloud.cache.basic.RemoteSessionProvider;
import com.oracle.cloud.cache.basic.SessionProvider;
import com.oracle.cloud.cache.basic.options.Expiry;
import com.oracle.cloud.cache.basic.options.Transport;
import nl.amis.smeetsm.springboot.person.Person;

public class PersonCache {
	private static PersonCache personCacheInstance;
	private final Logger log = LoggerFactory.getLogger(PersonCache.class);
	private final String CACHE_NAME = "test-cache";
	private Cache<Person> CACHE;
	
	public Cache<Person> getCACHE() {
		return CACHE;
	}
	
	protected PersonCache() {
			log.info("Constructor called");
			String protocolName = Optional.ofNullable(System.getenv("CACHING_PROTOCOL")).orElse("REST");
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
				CACHE = sessionProvider.createSession().getCache(CACHE_NAME, new PersonLoader(),
						Expiry.never(), new PersonSerializer());
				
			} else {
				String cacheUrl = "http://" + cacheHost + ":" + port + "/" + cacheUrlSuffix;
				log.info("Cache URL - " + cacheUrl);
				sessionProvider = new RemoteSessionProvider(cacheUrl);
				//CACHE = sessionProvider.createSession(transport).getCache(CACHE_NAME, new PersonLoader(),
				//		Expiry.of(Integer.valueOf(expiry), TimeUnit.SECONDS), new PersonSerializer());
				CACHE = sessionProvider.createSession(transport).getCache(CACHE_NAME, new PersonLoader(),
						Expiry.never(), new PersonSerializer());
			}
			
			if (CACHE == null) {
				log.info("CACHE is null");
			}
			log.info("Constructor done");
	}
	
	static PersonCache getInstance() {
		if (personCacheInstance == null) {
			personCacheInstance = new PersonCache(); 
			
		}
		return personCacheInstance;
	}

}
