package nl.amis.smeetsm.springboot.person;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.cloud.cache.metrics.CacheMetrics;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Controller for Persons")
@RestController
public class PersonController {
	
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;

	@ApiOperation("Gets all persons")
	@RequestMapping(method=RequestMethod.GET,value="/persons")
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}
	
	@ApiOperation("Gets a person on Id")
    @RequestMapping(method=RequestMethod.GET,value="/persons/{id}")
	public Person getPerson(@PathVariable Long id) {
		return personService.getPerson(id);
	}
	
	@ApiOperation("Deletes a person")
	@RequestMapping(method=RequestMethod.DELETE,value="/persons/{id}")
	public void deletePerson(@PathVariable Long id) {
		personService.deletePerson(id);
	}
    
	@ApiOperation("Deletes all persons")
	@RequestMapping(method=RequestMethod.DELETE,value="/persons")
	public void deletePerson() {
		personService.deletePersons();
	}
	
	@ApiOperation("Add a person")
    @RequestMapping(method=RequestMethod.POST,value="/persons")
	public Person addPerson(@RequestBody Person Person) {
		return personService.addPerson(Person);
	}

	@ApiOperation("Update a person")
	@RequestMapping(method=RequestMethod.PUT,value="/persons/{id}")
	public Person addPerson(@RequestBody Person Person,@PathVariable Long id) {
		return personService.updatePerson(id,Person);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/metrics")
	public CacheMetrics getMetrics() {
		return personService.getMetrics();
	}

}
