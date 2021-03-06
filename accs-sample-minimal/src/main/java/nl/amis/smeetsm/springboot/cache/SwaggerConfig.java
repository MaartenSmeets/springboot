package nl.amis.smeetsm.springboot.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//gives http://localhost:8080/swagger-ui.html
//and http://localhost:8080/v2/api-docs

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("nl.amis.smeetsm.springboot.person"))
                .paths(PathSelectors.regex("/.*persons.*"))
                .build()
                .enableUrlTemplating(true);
             
    }
}
