package org.sid.web;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import org.sid.dao.UserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
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

	@PostMapping(value="/forgotPassword")
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

	@PutMapping("/users/{id}")
	public AppUser update(@PathVariable Long id, @RequestBody  AppUser u) {
		Iterator<AppRole> iterator = u.getRoles().iterator();
		 while (iterator.hasNext()) {
		        u.setId(id);
		        accountService.saveUser(u);
		        accountService.addRoleToUser(u.getUsername(), iterator.next().getRoleName());
		 }
		return appUser;
	}

	@DeleteMapping("/users/{id}")
	public boolean delete(@PathVariable Long id) {
		userRepository.delete(id);
		return true;
	}
	
}
