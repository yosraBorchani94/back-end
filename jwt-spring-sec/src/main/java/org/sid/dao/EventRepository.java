package org.sid.dao;

import org.sid.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository  extends JpaRepository<Event, Long>, CrudRepository<Event, Long> {
}
