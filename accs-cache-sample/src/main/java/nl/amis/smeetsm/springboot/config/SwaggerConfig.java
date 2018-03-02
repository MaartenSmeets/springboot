package nl.amis.smeetsm.springboot.config;

import java.net.MalformedURLException;
import java.net.URL;
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
	
	private URL getURL() throws MalformedURLException {
    	URL pubURL = null;
		try {
			//If this works, you're very likely running on ACCS
			pubURL = new URL(System.getenv("ORA_APP_PUBLIC_URL"));
    		port=String.valueOf(pubURL.getPort());
		} catch (MalformedURLException e) {
    		//Running locally
    		pubURL = new URL("http://localhost:"+port);
    	}
		log.info("Swagger using URL: "+pubURL.toString());
		return pubURL;
	}
	
	@Bean
    public Docket productApi() throws MalformedURLException {
    	Set<String> protocols = new HashSet<String>();
    	URL pubURL = this.getURL();
    	protocols.add(pubURL.getProtocol());
    	
    	return new Docket(DocumentationType.SWAGGER_2)
        		.host(pubURL.getHost()+":"+String.valueOf(pubURL.getPort()))
        		.protocols(protocols)
                .select()                 .apis(RequestHandlerSelectors.basePackage("nl.amis.smeetsm.springboot.person"))
                .paths(PathSelectors.regex("/.*persons.*"))
                .build()
                .enableUrlTemplating(true);
    }
}
