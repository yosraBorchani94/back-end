package org.sid.dao;

import org.sid.entities.AcceptedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AcceptedUserRepository  extends JpaRepository<AcceptedUsers, Long>,CrudRepository<AcceptedUsers, Long> {

}
