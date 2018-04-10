package org.sid.dao;


import org.sid.entities.ModuleInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ModuleInstanceRepository extends JpaRepository<ModuleInstance, Long>, CrudRepository<ModuleInstance, Long> {

	public ModuleInstance findByIdUser(Long id);
	
	
}
