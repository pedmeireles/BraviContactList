package com.bravi.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.bravi.contact.Contact;
import com.bravi.contact.ContactRepository;
import com.bravi.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
    private ContactRepository contactRepository;
	
	@GetMapping("/person")
	public Page<Person> getAllPersons(Pageable pageable){
			return personRepository.findAll(pageable);
		
	}
	
	@GetMapping("/person/{personId}")
	public Person getOnePerson (
			@PathVariable Long personId
			) throws ResourceNotFoundException {
			return personRepository.findById(personId).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("personId " + personId + " not found"));
	}
	
	@PostMapping("/person")
	public Person createPerson(@Valid @RequestBody Person person) {
		return personRepository.save(person);
	}
	@PutMapping("/person/{personId}")
	public Person updatePerson(@PathVariable long personId,
			@Valid @RequestBody Person personRequest) {
		return personRepository.findById(personId).map(person -> {
			person.setName(personRequest.getName());
			return personRepository.save(person);
		}).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("personId " + personId + " not found"));
	}

	@DeleteMapping("/person/{personId}")
	public Map<String, Boolean> deletePerson(@PathVariable long personId) throws ResourceNotFoundException{
		Person person =  personRepository.findById(personId).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("personId " + personId + " not found"));


		List<Contact> contactLists = contactRepository.findAllByPersonId(personId);
		
		contactLists.forEach(contact -> contactRepository.delete(contact));
		personRepository.delete(person);

		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
