package org.sid.web;

import java.util.List;
import org.sid.dao.RoleRepository;
import org.sid.entities.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class RoleRestController {
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/roles")
	public List<AppRole> listRoles(){
		return roleRepository.findAll();
	}
	
	@GetMapping("/roles/{id}")
	public AppRole getRole(@PathVariable Long id){
		return roleRepository.findOne(id);
	}
		
	@PostMapping("/roles")
	public AppRole save(@RequestBody AppRole t){
		return roleRepository.save(t);
	}
	
	@PutMapping("/roles/{id}")
	public AppRole update (@PathVariable Long id,@RequestBody AppRole t) {
		t.setId(id);
		return roleRepository.save(t);
	}
	
	@DeleteMapping("/roles/{id}")
	public boolean delete(@PathVariable Long id){
		roleRepository.delete(id);
		return true ;
	}
	
	@GetMapping("/findByRoleName/{roleName}")
	public AppRole getRole(@PathVariable String roleName){
		return roleRepository.findByRoleName(roleName);
	}

}
