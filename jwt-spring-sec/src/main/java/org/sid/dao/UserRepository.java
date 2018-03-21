package org.sid.dao;

import org.sid.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository  extends JpaRepository<AppUser, Long>, CrudRepository<AppUser, Long>{
	
public AppUser findByUsername (String username) ;

}
