package org.sid;

import java.util.stream.Stream;
import javax.annotation.Resource;
import org.sid.dao.EventRepository;
//import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.sid.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.sid.uploadfile.StorageService;;

@SpringBootApplication
public class JwtSpringSecApplication implements CommandLineRunner{
  
    @Autowired
   	private EventRepository eventRepository;
    @Autowired
    private AccountService accountService;
    @Resource
	StorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder getBCPE () {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... arg0) throws Exception {

		//storageService.deleteAll();
		//storageService.init();
	    /*accountService.saveUser(new AppUser(null,"admin","1234","ADMIN ROLE"));
		accountService.saveUser(new AppUser(null,"user","1234","USER ROLE"));
		accountService.saveRole(new AppRole(null,"ADMIN ROLE" ));
		accountService.saveRole(new AppRole(null,"USER ROLE" ));
		accountService.addRoleToUser("admin", "ADMIN ROLE");
		accountService.addRoleToUser("admin", "USER ROLE");
		accountService.addRoleToUser("user", "USER ROLE");
		Stream.of("T1","T2","T3").forEach(t->{
			taskRepository.save(new Task(null,t));
		});
		
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});*/
		
		}
	}

