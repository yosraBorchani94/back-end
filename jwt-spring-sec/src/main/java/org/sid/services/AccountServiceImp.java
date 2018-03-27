package org.sid.services;

import java.util.Iterator;

import org.sid.dao.RoleRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImp implements AccountService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AppUser saveUser(AppUser user) {
		String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPW);
		return userRepository.save(user);
		
	}

	@Override
	public AppRole saveRole(AppRole role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppRole role = roleRepository.findByRoleName(roleName);
		AppUser user = userRepository.findByUsername(username);
		user.getRoles().add(role);		
	}

	@Override
	public AppUser findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	

	@Override
	public boolean DeleteRoleFromUser(Long id) {
		AppUser user =  userRepository.findOne(id);
		System.out.println("username ** "+user.getUsername() + " " + user.getRoles().size());
		if(user.getRoles().size()>0 ) {
			Iterator<AppRole> iterator = user.getRoles().iterator();
			AppRole role = roleRepository.findByRoleName(iterator.next().getRoleName());
			System.out.println("role id***  "  + role.getId() +" role name "+ role.getRoleName());
			if (user.getRoles().remove(role)) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void addRoleToUser2(Long id, String roleName) {
		//System.out.println("---in addRoleToUser2---");
		//System.out.println("id----  "  +id +" rolename "+ roleName);
		AppUser user =  userRepository.findOne(id);
		System.out.println("username --- "  + user.getUsername() );
		AppRole role = roleRepository.findByRoleName(roleName);
		user.getRoles().add(role);		
	}




}
