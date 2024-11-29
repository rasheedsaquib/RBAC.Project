package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
	
}
