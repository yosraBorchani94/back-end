package org.sid.services;


import org.sid.entities.AppRole;
import org.sid.entities.AppUser;




public interface AccountService {

	public AppUser saveUser (AppUser user);
	public AppRole saveRole (AppRole role);
    public void addRoleToUser (String username,String roleName);
    public void addRoleToUser2 (Long id,String roleName);
    public boolean DeleteRoleFromUser (Long id);
    public AppUser findUserByUsername (String username);
    
}
