package nl.amis.smeetsm.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApplication {
	private static final Logger log = LoggerFactory.getLogger(CourseApplication.class);
	
	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(CourseApplication.class, args);
	}
}
