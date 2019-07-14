package com.bravi.contact;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository  extends JpaRepository<Contact, Long>{
	Page<Contact> findAllByPersonId(Long personId, Pageable pageable);
	
	Optional<Contact> findByIdAndPersonId(Long id, Long personId);
	List<Contact> findAllByPersonId(Long personId);
}
