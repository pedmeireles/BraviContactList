package com.bravi.contact;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.bravi.exception.ResourceNotFoundException;
import com.bravi.person.Person;
import com.bravi.person.PersonRepository;

import org.apache.catalina.startup.ClassLoaderFactory.RepositoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class ContactController {
	
	@Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PersonRepository personRepository;
	
	@GetMapping("/{personId}/contact")
	public Page<Contact> getAllcontactsFromPerson(
        @PathVariable Long personId,
    Pageable pageable){
			return contactRepository.findAllByPersonId(personId,pageable);
		
	}
    
    @GetMapping("/{personId}/contact/{contactId}")
	public Contact getOnecontact(
            @PathVariable("personId") Long personId,
            @PathVariable("contactId") Long contactId

			) throws ResourceNotFoundException{
			return contactRepository.findByIdAndPersonId(contactId, personId).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("The contact of this user wasn't found."));
	}
    
    @PostMapping("/{personId}/contact")
	public Contact createContactForPerson(
        @PathVariable Long personId,
        @Valid @RequestBody Contact contact) throws ResourceNotFoundException{

        Optional<Person> person = personRepository.findById(personId);
        if(person.isPresent()){
            Person definelyAPerson = person.get();
            contact.setPerson(definelyAPerson);
            return contactRepository.save(contact);
        } else{
            throw new com.bravi.exception.ResourceNotFoundException("personId " + personId + " not found. Can't create contact.");
        }
	}
    
    @PutMapping("/{personId}/contact/{contactId}")
	public Contact updateContactOfUser(@PathVariable("personId") long personId,
        @PathVariable("contactId") long contactId,
        @Valid @RequestBody Contact contactRequest) {
		return contactRepository.findByIdAndPersonId(contactId, personId).map(contact -> {
            contact.setValue(contactRequest.getValue());
            contact.setType(contactRequest.getType());
			return contactRepository.save(contact);
		}).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("Doesn't found one of them: The contact, or the person."));
    }
    
    @DeleteMapping("/{personId}/contact/{contactId}")
    public Map<String, Boolean> deleteContactWithPerson(@PathVariable("personId") long personId,
    @PathVariable("contactId") long contactId) throws ResourceNotFoundException{
        Contact contact =  contactRepository.findByIdAndPersonId(contactId, personId).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("The contact of this user wasn't found."));

		contactRepository.delete(contact);

		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
    }
    
    @DeleteMapping("contact/{contactId}")
    public Map<String, Boolean> deleteContact(@PathVariable("contactId") long contactId) throws ResourceNotFoundException{
        Contact contact =  contactRepository.findById(contactId).orElseThrow(() -> new com.bravi.exception.ResourceNotFoundException("The contact of this user wasn't found."));

		contactRepository.delete(contact);

		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
