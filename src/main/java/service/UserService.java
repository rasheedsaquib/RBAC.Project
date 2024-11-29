package service;

import java.util.List;


import main.Role;
import main.User;

public interface UserService {
	
	User saveUser(User user);
	Role saveRole(Role role);
	
	void addRoleToUser(String username, String roleName);
	User getUser(String username);
	List<User>getUsers();

}
