package org.sid.web;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sid.dao.ContactUsRepository;
import org.sid.dao.DocumentRepository;
import org.sid.dao.EventRepository;
import org.sid.dao.ModuleInstanceRepository;
import org.sid.dao.QuizInstanceRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.ContactUs;
import org.sid.entities.Document;
import org.sid.entities.Event;
import org.sid.entities.ModuleInstance;
import org.sid.entities.QuizInstance;
import org.sid.mail.MailService;
import org.sid.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
public class AccountRestController {
	private static SecureRandom random = new SecureRandom();
	private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC = "0123456789";

	int len = 6;

	@Autowired
	private MailService mailService;
	@Autowired
	private AccountService accountService;
	AppUser appUser = null;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ModuleInstanceRepository moduleInstanceRepository;

	@Autowired
	private QuizInstanceRepository quizInstanceRepository;

	@Autowired
	private ContactUsRepository contactUsRepository;

	@Autowired
	private DocumentRepository documentRepository;

	public static String generatePassword(int len, String dic) {
		String result = "";
		for (int i = 0; i < len; i++) {

			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm userForm) {
		if (!userForm.getPassword().equals(userForm.getRepassword()))
			throw new RuntimeException("You must confirm your password");
		AppUser user = accountService.findUserByUsername(userForm.getUsername());
		if (user != null)
			throw new RuntimeException("this user already exists ");
		AppUser appUser = new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		accountService.saveUser(appUser);
		accountService.addRoleToUser(userForm.getUsername(), userForm.getRole().getRoleName());
		return appUser;
	}

	@PostMapping(value = "/forgotPassword")
	public String forgotPassword(@RequestBody AppUser u) {
		String generatedString = null;
		AppUser user = accountService.findUserByUsername(u.getUsername());
		if (user != null) {
			String password = generatePassword(len, NUMERIC + ALPHA);
			user.setPassword(password);
			accountService.saveUser(user);
			AppUser mailUser = new AppUser();
			mailUser.setUsername(u.getUsername());
			mailUser.setPassword(password);
			try {
				mailService.sendNotification(mailUser);
			} catch (MailException e) {
				System.out.println(e);
			}
		}
		return "success";
	}

	@GetMapping("/users")
	public List<AppUser> listUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public AppUser getUser(@PathVariable Long id) {
		return userRepository.findOne(id);
	}

	@GetMapping("/getUsersWithRole")
	public List<UserForm> getUsersWithRole() {
		List<UserForm> userFrom = new ArrayList<>();
		 List<AppUser> users = userRepository.findAll();
		 for(int i =0 ; i<users.size() ; i++) {
			 if(users.get(i).getRoles().size()>0 ) {
				 UserForm form = new UserForm();
					Iterator<AppRole> iterator = users.get(i).getRoles().iterator();
					 form.setRole(iterator.next().getRoleName());
					 form.setId(users.get(i).getId());
					 form.setUsername(users.get(i).getUsername());
					 form.setPassword(users.get(i).getPassword());
					 userFrom.add(form);
			 }
		 }
		 return userFrom;
	}

	@PutMapping("/users/{id}")
	public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser u) {
		u.setId(id);
		accountService.saveUser(u);
		if (accountService.DeleteRoleFromUser(id)) {
			accountService.addRoleToUser2(id, u.getRoles().iterator().next().getRoleName());
		}
		return u;
	}

	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable Long id) {
		AppUser user = userRepository.findOne(id);

		/**** delete module instance ****/
		List<ModuleInstance> moduleInstance = moduleInstanceRepository.findAll();
		if (moduleInstance.size() > 0) {
			for (int i = 0; i < moduleInstance.size(); i++) {
				if (moduleInstance.get(i).getUser().getId() == user.getId()) {

					/**** delete Quiz instance ****/
					List<QuizInstance> quizInstance = quizInstanceRepository.findAll();
					if (quizInstance.size() > 0) {
						for (int j = 0; j < quizInstance.size(); j++) {
							if (quizInstance.get(j).getModuleInstance().getId() == moduleInstance.get(i).getId()) {
								quizInstanceRepository.delete(quizInstance.get(j).getId());
							}
						}
					}
					moduleInstanceRepository.delete(moduleInstance.get(i).getId());
				}
			}
		}

		/**** delete contactUs user ****/
		List<ContactUs> contactUs = contactUsRepository.findAll();
		if (contactUs.size() > 0) {
			for (int i = 0; i < contactUs.size(); i++) {
				if (contactUs.get(i).getUser().getId() == user.getId()) {
					contactUsRepository.delete(contactUs.get(i).getId());
				}
			}
		}

		/**** delete document user ****/
		List<Document> document = documentRepository.findAll();
		if (document.size() > 0) {
			for (int i = 0; i < document.size(); i++) {
				if (document.get(i).getUser().getId() == user.getId()) {
					documentRepository.delete(document.get(i).getId());
				}
			}
		}

		/***** delete AppUser- roles ****/
		accountService.DeleteRoleFromUser(id);

		/**** delete event-user ****/
		List<Event> events = eventRepository.findAll();

		if (events.size() > 0) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getUser().size() > 0) {
					Iterator<AppUser> iterator = events.get(i).getUser().iterator();
					if (iterator.next().getId() == user.getId()) {
						events.get(i).getUser().remove(user);
					}
				}
			}

		}

		userRepository.delete(id);

	}

	@GetMapping("/userByUsername/{username:.*}")
	public AppUser getUserByUsername(@PathVariable String username) {
		AppUser user = accountService.findUserByUsername(username);
		return user;
	}

}
