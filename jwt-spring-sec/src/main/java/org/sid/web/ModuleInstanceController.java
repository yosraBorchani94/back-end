package org.sid.web;

import org.sid.dao.ModuleInstanceRepository;
import org.sid.dao.ModuleRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Module;
import org.sid.entities.ModuleInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@CrossOrigin("*")
public class ModuleInstanceController {
	
	@Autowired
	private ModuleInstanceRepository moduleInstanceRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/addUserToModule")
	public ModuleInstance addUserToModule(@RequestParam("idModule") Long idModule, @RequestParam("idUser")  Long idUser , @RequestParam("score")  int score) {
	
		Module m = moduleRepository.findOne(idModule);
		AppUser u = userRepository.findOne(idUser);
		ModuleInstance mi = new ModuleInstance();
		mi.setModule(m);
		mi.setUser(u);
		mi.setScore(score);
		return moduleInstanceRepository.save(mi);
	}
}
