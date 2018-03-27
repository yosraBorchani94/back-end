package org.sid.dao;

import org.sid.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ModuleRepository extends JpaRepository<Module, Long>,CrudRepository<Module, Long> {

}
