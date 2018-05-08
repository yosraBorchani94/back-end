package org.sid.web;

import java.util.List;

import org.sid.dao.ContactUsRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppUser;
import org.sid.entities.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ContactUsController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactUsRepository contactUsRepository;
	
	@PostMapping("/sendMessage")
	public void sendMessage (@RequestBody ContactUs contactUs) {
		contactUsRepository.save(contactUs);
	}
	
	@GetMapping("/getMessage")
	public List<ContactUs> getMessage() {
		return contactUsRepository.findAll();
	}
	
	
}
