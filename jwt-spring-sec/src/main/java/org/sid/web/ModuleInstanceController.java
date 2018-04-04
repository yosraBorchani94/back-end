package org.sid.web;

import org.sid.dao.ModuleInstanceRepository;
import org.sid.entities.Module;
import org.sid.entities.ModuleInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class ModuleInstanceController {
	
	
	@Autowired
	private ModuleInstanceRepository moduleInstanceRepository;
	
	
	
	@PostMapping("/addUserToModule")
	public ModuleInstance addUserToModule(@RequestBody ModuleInstance moduleInstance) {
		ModuleInstance mi = new ModuleInstance();
		mi.setIdModule(moduleInstance.getIdModule());
		mi.setIdUser(moduleInstance.getIdUser());
		return moduleInstanceRepository.save(mi);
	}
}
