package nl.amis.smeetsm.springboot;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccsCacheSampleApplication {

	public static void main(String[] args) {
		//handy for ACCS logging
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
		    System.out.format("%s=%s%n", envName, env.get(envName));
		}
		SpringApplication.run(AccsCacheSampleApplication.class, args);
	}
}
