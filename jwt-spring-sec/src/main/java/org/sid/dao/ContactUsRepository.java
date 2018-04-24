package org.sid.dao;

import org.sid.entities.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long>, CrudRepository<ContactUs, Long> {
	
}
