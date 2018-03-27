package org.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.sid.dao.ModuleRepository;
import org.sid.entities.Module;
import org.sid.services.ModuleService;



@RestController
@CrossOrigin("*")
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ModuleRepository moduleRepository;
	
	@PostMapping("/module")
	public Module addModule(@RequestBody Module module) {
		return moduleRepository.save(module);
	}
	
	@GetMapping("/module")
	public List<Module> listModules(){
		return moduleRepository.findAll();
	}
	
	@DeleteMapping("/module/{id}")
	public boolean delete(@PathVariable Long id){
		moduleRepository.delete(id);
		return true ;
	}
	
	
	@GetMapping("/module/{id}")
	public Module getModule(@PathVariable Long id){
		return moduleRepository.findOne(id);
	}
	
	@PutMapping("/module/{id}")
	public Module updateModule (@PathVariable Long id,@RequestBody Module m) {
		m.setId(id);
		return moduleRepository.save(m);
	}
	
	
}
