package nl.amis.smeetsm.springboot.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import nl.amis.smeetsm.springboot.person.PersonService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//gives http://localhost:8080/swagger-ui.html
//and http://localhost:8080/v2/api-docs

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    
	private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
	
	@Value("${server.port}")
	private String port;
	
	@Bean
    public Docket productApi() {
    	String hostname = Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost");
    	String port="";
    	Set<String> protocols = new HashSet<String>();
    	if (System.getenv("PORT") != null) {
    		//Running on ACCS
    		log.info("Swagger using URL: ");
    		port="443";
    		protocols.add("https");
    		log.info("Swagger using URL: https://"+hostname+":"+port);
    		
    	} else {
    		//Running locally
    		port=this.port;
    		protocols.add("http");
    		log.info("Swagger using URL: http://"+hostname+":"+port);
    	}
    	
    	return new Docket(DocumentationType.SWAGGER_2)
        		.host(hostname+":"+port)
        		.protocols(protocols)
                .select()                 .apis(RequestHandlerSelectors.basePackage("nl.amis.smeetsm.springboot.person"))
                .paths(PathSelectors.regex("/.*persons.*"))
                .build()
                .enableUrlTemplating(true);
    }
}
